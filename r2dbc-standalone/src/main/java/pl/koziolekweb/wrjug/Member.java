package pl.koziolekweb.wrjug;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

	private int memId;

	private String surname;

	private String firstName;

	private String address;

	private int zipCode;

	private String telephone;

	private Integer recommendedBy;

	private LocalDateTime joinDate;
}
