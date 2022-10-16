package pl.koziolekweb.wrjug;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import pl.koziolekweb.wrjug.r2dbc.model.Member;

@SpringBootTest
class ReactiveClient {

	WebClient webClient;

	@BeforeEach
	void setUp() {
		webClient = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}

	@Test
	void getAllMembers() {
		webClient.get()
				.uri("/r2dbc/member")
				.exchangeToFlux(
						cr->
						{
							System.out.println("***");
							return cr.bodyToFlux(Member.class);
						}

				).subscribe(
						m-> System.out.println(m)
				);

		sleep();
	}

	private static void sleep() {
		try {
			Thread.sleep(5000L);
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
