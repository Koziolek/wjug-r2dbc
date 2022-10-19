package pl.koziolekweb.wrjug;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.mapstruct.MappingTarget;


public interface RowMapper<T> {

	T fromRow(Row row, RowMetadata metadata);

	default <O> O get(String name, Row row, RowMetadata metadata) {
		final Class<O> javaType = (Class<O>) metadata.getColumnMetadata(name).getJavaType();
		return row.get(name, javaType);
	}
}
