package pl.koziolekweb.wrjug.nospring;

import java.time.LocalDateTime;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import pl.koziolekweb.wrjug.r2dbc.model.Member;

class MemberExtractor implements Extractor<Member> {
    @Override
    public Member extract(Row row, RowMetadata rowMetadata) {
        return Member.builder()
                .memId(get(row, "memid", Integer.class, 0))
                .firstName(get(row, "firstname", String.class, ""))
                .surname(get(row, "surname", String.class, ""))
                .address(get(row, "address", String.class, ""))
                .zipCode(get(row, "zipcode", Integer.class, 0))
                .telephone(get(row, "telephone", String.class))
                .recommendedBy(get(row, "recommendedby", Integer.class))
                .joinDate(get(row, "joindate", LocalDateTime.class))
                .build();
    }

    private <V> V get(Row row, String col, Class<V> type, V def) {
        V v = row.get(col, type);
        return v != null ? v : def;
    }
    private <V> V get(Row row, String col, Class<V> type) {
        return row.get(col, type);
    }
}
