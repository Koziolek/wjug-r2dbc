package pl.koziolekweb.wrjug.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pl.koziolekweb.wrjug.r2dbc.model.Member;

public interface MemberReactiveRepository extends R2dbcRepository<Member, Integer> {


}
