package pl.koziolekweb.wrjug.jpa;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {

	@Id
	@Column(name = "memid")
	private int memId;

	@Column(name = "surname")
	private String surname;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "address")
	private String address;

	@Column(name = "zipcode")
	private int zipCode;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "recommendedby")
	private Integer recommendedBy;

	@Column(name = "joindate")
	private LocalDateTime joinDate;
}
