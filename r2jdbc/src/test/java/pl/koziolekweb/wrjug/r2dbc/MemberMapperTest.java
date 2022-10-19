package pl.koziolekweb.wrjug.r2dbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class MemberMapperTest {

	R2dbcMemberMapper memberMapper = Mappers.getMapper(R2dbcMemberMapper.class);

	@Test
	void shouldCopyOnlyNonNullProperties() {
		var dto = new MemberDTO();
		dto.setMemId(10);
		dto.setFirstName("Joe");
		var entity = new Member();
		entity.setMemId(10);
		entity.setFirstName("Jane");
		entity.setSurname("Smith");
		entity.setAddress("London");
		entity.setZipCode(12345);
		entity.setRecommendedBy(null);

		var member = memberMapper.putDtoToEntity(dto, entity);

		Assertions.assertThat(member).isSameAs(entity);
		Assertions.assertThat(member.getFirstName()).isEqualTo("Joe");
		Assertions.assertThat(member.getSurname()).isEqualTo("Smith");
		Assertions.assertThat(member.getAddress()).isEqualTo("London");
		Assertions.assertThat(member.getZipCode()).isEqualTo(12345);

	}
}