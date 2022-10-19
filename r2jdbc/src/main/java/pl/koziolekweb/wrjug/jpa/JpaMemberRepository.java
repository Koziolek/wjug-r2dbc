package pl.koziolekweb.wrjug.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, Integer> {
	@Query("select m from Member m where m.recommendedBy = ?1")
	List<Member> recomendedBy(Integer id);
}
