package pl.koziolekweb.wrjug.r2dbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Wrapped;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationService {


	private final ConnectionFactory connectionFactory;
	private final Set<NotificationTopic> watchedTopics = Collections.synchronizedSet(new HashSet<>());

	@Qualifier("postgres-event-mapper")
	private final ObjectMapper objectMapper;

	private PostgresqlConnection connection;


	@PreDestroy
	private void preDestroy() {
		this.getConnection().close().subscribe();
	}

	private PostgresqlConnection getConnection() {
		if(connection == null) {
			synchronized(NotificationService.class) {
				if(connection == null) {
					try {
						connection = Mono.from(connectionFactory.create())
								.cast(Wrapped.class)
								.map(Wrapped::unwrap)
								.cast(PostgresqlConnection.class)
								.toFuture().get();
					} catch(InterruptedException e) {
						throw new RuntimeException(e);
					} catch(ExecutionException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return this.connection;
	}

	public <T> Flux<T> listen(final NotificationTopic topic, final Class<T> clazz) {

		if(!watchedTopics.contains(topic)) {
			executeListenStatement(topic);
		}

		return getConnection().getNotifications()
				.log("notifications")
				.filter(notification -> topic.name().equals(notification.getName()) && notification.getParameter() != null)
				.handle((notification, sink) -> {
					final String json = notification.getParameter();
					if(!StringUtils.isBlank(json)) {
						try {
							sink.next(objectMapper.readValue(json, clazz));
						} catch(JsonProcessingException e) {
							log.error(String.format("Problem deserializing an instance of [%s] " +
									"with the following json: %s ", clazz.getSimpleName(), json), e);
							Mono.error(new DeserializationException(topic, e));
						}
					}
				});
	}

	private void executeListenStatement(final NotificationTopic topic) {
		getConnection().createStatement(String.format("LISTEN \"%s\"", topic)).execute()
				.doOnComplete(() -> watchedTopics.add(topic))
				.subscribe();
	}

	public void unlisten(final NotificationTopic topic) {
		if(watchedTopics.contains(topic)) {
			executeUnlistenStatement(topic);
		}
	}

	private void executeUnlistenStatement(final NotificationTopic topic) {
		getConnection().createStatement(String.format("UNLISTEN \"%s\"", topic)).execute()
				.doOnComplete(() -> watchedTopics.remove(topic))
				.subscribe();
	}
}
