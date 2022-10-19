package pl.koziolekweb.wrjug;

import static java.time.LocalDateTime.now;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import java.util.function.LongToIntFunction;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SimpleApp {

	private static final String ALL_MEMBERS = "Select * FROM members";
	private static String INSERT_BOOKING = """
			INSERT INTO bookings (facid, memid, starttime, slots)
			 VALUES ($1, $2, $3, $4)
			""";
	private static MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

	public static void main(String[] args) {
		var options = ConnectionFactoryOptions.builder()
				.option(ConnectionFactoryOptions.HOST, "localhost")
				.option(ConnectionFactoryOptions.PORT, 15432)
				.option(ConnectionFactoryOptions.DATABASE, "postgres")
				.option(ConnectionFactoryOptions.DATABASE, "postgres")
				.option(ConnectionFactoryOptions.USER, "r2dbc")
				.option(ConnectionFactoryOptions.PASSWORD, "r2dbc")
				.option(ConnectionFactoryOptions.DRIVER, "postgresql")
				.build();
		var connectionFactory = ConnectionFactories.get(options);
		var conn = connectionFactory.create();


		Flux.from(conn)
				.map(c -> c.createStatement(ALL_MEMBERS))
				.flatMap(Statement::execute)
				.log()
				.flatMap(r -> r.map(memberMapper::fromRow))
				.log()
				.map(Member::getMemId)
				.reduce(0, Integer::sum)
				.doOnNext(System.out::println)
				.block();


		final Object o = Flux.from(conn)
				.map(
						c -> {
							c.beginTransaction();
							c.setAutoCommit(true);
							return c;
						}
				).map(
						c -> c.createStatement(INSERT_BOOKING)
								.bind(0, 1)
								.bind(1, 1)
								.bind(2, now())
								.bind(3, 1)
				).flatMap(Statement::execute)
				.flatMap(Result::getRowsUpdated)
				.log("INSERTS: ")
				.blockLast();

		System.out.println(o);

		Mono.from(conn).map(Connection::close).subscribe();
	}
}
