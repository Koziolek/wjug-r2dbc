package pl.koziolekweb.wrjug.r2dbc;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, componentModel = "spring")
interface R2dbcMemberMapper {

	Member putDtoToEntity(MemberDTO memberDTO, @MappingTarget Member target);

	@Condition
	default boolean avoidMapDefaults(int value){
		return value != 0;
	}


}
