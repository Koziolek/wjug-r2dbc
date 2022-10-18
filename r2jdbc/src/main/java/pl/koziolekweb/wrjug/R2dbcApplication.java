package pl.koziolekweb.wrjug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pl.koziolekweb.wrjug.*")
public class R2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(R2dbcApplication.class, args);
	}

}