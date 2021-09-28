package pl.koziolekweb.wrjug.r2dbc;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "pl.koziolekweb.wrjug.r2dbc")
class R2dbcConfiguration {

}
