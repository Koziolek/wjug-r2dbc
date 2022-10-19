package pl.koziolekweb.wrjug.r2dbc;


import static pl.koziolekweb.wrjug.r2dbc.NotificationTopic.MEMBER_DELETED;
import static pl.koziolekweb.wrjug.r2dbc.NotificationTopic.MEMBER_SAVED;

import java.time.Duration;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/r2dbc/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	private final NotificationService notificationService;

	@GetMapping
	public Flux<Member> all() {
		return memberRepository.findAll();
	}


	@GetMapping("/{id}")
	public Mono<Member> get(@PathVariable("id") Integer id) {
		return memberRepository.findById(id);
	}

	@PostMapping("/")
	public Mono<Member> save(@RequestBody @Valid Member newMember) {
		return memberRepository.save(newMember);
	}

	@PutMapping("/")
	public Mono<Member> save(@RequestBody @Valid MemberDTO updatedMember) {
		return memberRepository.findById(updatedMember.getMemId())
				.map(m -> memberMapper.putDtoToEntity(updatedMember, m))
				.flatMap(m -> memberRepository.save(m));

	}

	@DeleteMapping("/{id}")
	public Mono<Member> delete(@PathVariable("id") Integer id) {
		return memberRepository
				.findById(id)
				.flatMap(member ->
						memberRepository.delete(member)
								.map(unused -> member)
				);
	}

	@GetMapping("/recomended/{id}")
	public Flux<Member> recomendedBy(@PathVariable("id") Integer id) {
		return memberRepository.recomendedBy(id);
	}

	@GetMapping("/events")
	public Flux<ServerSentEvent<Object>> listenToEvents() {

		return Flux.merge(listenToDeletedItems(), listenToSavedItems())
				.map(o -> ServerSentEvent.builder()
						.retry(Duration.ofSeconds(4L))
						.event(o.getClass().getName())
						.data(o).build()
				);

	}

	@GetMapping("/unevents")
	public Mono<ResponseEntity<Void>> unlistenToEvents() {
		unlistenToDeletedItems();
		unlistenToSavedItems();
		return Mono.just(
				ResponseEntity
						.status(HttpStatus.I_AM_A_TEAPOT)
						.body(null)
		);
	}

	private Flux<Member> listenToSavedItems() {
		return this.notificationService.listen(MEMBER_SAVED, Member.class);
	}

	private Flux<Integer> listenToDeletedItems() {

		return this.notificationService.listen(MEMBER_DELETED, Member.class)
				.map(Member::getMemId);
	}

	private void unlistenToSavedItems() {
		this.notificationService.unlisten(MEMBER_SAVED);
	}

	private void unlistenToDeletedItems() {

		this.notificationService.unlisten(MEMBER_DELETED);
	}

}
