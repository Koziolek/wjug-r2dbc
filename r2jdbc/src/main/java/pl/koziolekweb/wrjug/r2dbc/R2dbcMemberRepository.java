package pl.koziolekweb.wrjug.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface R2dbcMemberRepository extends R2dbcRepository<Member, Integer> {
	@Query("Select * from members where recommendedby = :id")
	Flux<Member> recomendedBy(Integer id);
}
