package pl.koziolekweb.wrjug.r2dbc;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "pl.koziolekweb.wrjug.r2dbc")
public class R2dbcConfiguration {

	@Bean
	@Qualifier("postgres-event-mapper")
	public ObjectMapper postgresEventObjectMapper(){
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
