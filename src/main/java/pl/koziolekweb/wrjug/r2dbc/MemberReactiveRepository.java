package pl.koziolekweb.wrjug.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import org.springframework.stereotype.Repository;
import pl.koziolekweb.wrjug.r2dbc.model.Member;

@Repository
public interface MemberReactiveRepository extends R2dbcRepository<Member, Integer> {


}
