package pl.koziolekweb.wrjug.r2dbc.model;

import java.time.LocalDateTime;



import org.springframework.data.relational.core.mapping.*;

import lombok.Builder;
import lombok.Value;


@Value
@Builder(toBuilder = true)
@Table("members")
public class Member {

    @Column("memid")
    private int memId;
    @Column("surname")
    private String surname;
    @Column("firstname")
    private String firstName;
    @Column("address")
    private String address;
    @Column("zipcode")
    private int zipCode;
    @Column("telephone")
    private String telephone;
    @Column("recommendedby")
    private Integer recommendedBy;
    @Column("joindate")
    private LocalDateTime joinDate;
}
