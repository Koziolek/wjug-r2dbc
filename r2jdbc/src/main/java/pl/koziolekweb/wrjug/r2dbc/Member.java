package pl.koziolekweb.wrjug.r2dbc;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("members")
public class Member {

	@Id
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
