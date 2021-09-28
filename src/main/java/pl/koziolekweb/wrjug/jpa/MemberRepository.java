package pl.koziolekweb.wrjug.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.koziolekweb.wrjug.jpa.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
