package pl.koziolekweb.wrjug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "pl.koziolekweb.wrjug")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
