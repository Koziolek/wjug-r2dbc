package pl.koziolekweb.wrjug.jpa;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.koziolekweb.wrjug.jpa.model.Member;

@RestController
@RequestMapping("/jpa/member")
@RequiredArgsConstructor
class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("")
    public List<Member> members(){
        return memberRepository.findAll();
    }
}
