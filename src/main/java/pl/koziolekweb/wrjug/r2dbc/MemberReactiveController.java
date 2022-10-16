package pl.koziolekweb.wrjug.r2dbc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.koziolekweb.wrjug.r2dbc.model.Member;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/r2dbc/member")
@RequiredArgsConstructor
class MemberReactiveController {

	private final MemberReactiveRepository memberRepository;

	@GetMapping("")
	public Flux<Member> members() {
		return memberRepository.findAll()
				.doOnNext(System.out::println);
	}
}
