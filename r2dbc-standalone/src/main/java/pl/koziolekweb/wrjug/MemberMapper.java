package pl.koziolekweb.wrjug;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MemberMapper extends RowMapper<Member> {

	@Mapping(target = "memId", expression = "java(get(\"memId\", row, metadata))")
	@Mapping(target = "surname", expression = "java(get(\"surname\", row, metadata))")
	@Mapping(target = "firstName", expression = "java(get(\"firstName\", row, metadata))")
	@Mapping(target = "address", expression = "java(get(\"address\", row, metadata))")
	@Mapping(target = "zipCode", expression = "java(get(\"zipCode\", row, metadata))")
	@Mapping(target = "telephone", expression = "java(get(\"telephone\", row, metadata))")
	@Mapping(target = "recommendedBy", expression = "java(get(\"recommendedBy\", row, metadata))")
	@Mapping(target = "joinDate", expression = "java(get(\"joinDate\", row, metadata))")
	Member fromRow(Row row, RowMetadata metadata);
}
