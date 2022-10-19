package pl.koziolekweb.wrjug.jpa;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberDTO {

	@NotEmpty
	private int memId;
	private String surname;
	private String firstName;
	private String address;
	private int zipCode;
	private String telephone;
	private Integer recommendedBy;
	private LocalDateTime joinDate;
}
