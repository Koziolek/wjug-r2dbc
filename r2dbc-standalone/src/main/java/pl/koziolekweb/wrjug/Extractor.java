package pl.koziolekweb.wrjug;

import java.util.function.BiFunction;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

@FunctionalInterface
public interface Extractor<T> extends BiFunction<Row, RowMetadata, T> {

    T extract(Row row, RowMetadata rowMetadata);

    @Override
    default T apply(Row row, RowMetadata rowMetadata){
        return extract(row, rowMetadata);
    }
}
