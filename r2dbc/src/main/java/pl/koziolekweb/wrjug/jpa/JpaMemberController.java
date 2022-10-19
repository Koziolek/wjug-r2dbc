package pl.koziolekweb.wrjug.jpa;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa/member")
@RequiredArgsConstructor
class JpaMemberController {

	private final JpaMemberRepository memberRepository;
	private final JpaMemberMapper memberMapper;

	@GetMapping
	public List<Member> all() {
		return memberRepository.findAll();
	}


	@GetMapping("/{id}")
	public Optional<Member> get(@PathVariable("id") Integer id) {
		return memberRepository.findById(id);
	}

	@PostMapping("/")
	public Member save(@RequestBody @Valid Member newMember) {
		return memberRepository.save(newMember);
	}

	@PutMapping("/")
	public Optional<Member> save(@RequestBody @Valid MemberDTO updatedMember) {
		return memberRepository.findById(updatedMember.getMemId())
				.map(m -> memberMapper.putDtoToEntity(updatedMember, m))
				.map(m -> memberRepository.save(m));
	}

	@DeleteMapping("/{id}")
	public Optional<Member> delete(@PathVariable("id") Integer id) {
		return memberRepository
				.findById(id)
				.map(member -> {
							memberRepository.delete(member);
							return member;
						}
				);
	}

	@GetMapping("/recomended/{id}")
	public List<Member> recomendedBy(@PathVariable("id") Integer id) {
		return memberRepository.recomendedBy(id);
	}

}
