
package pl.koziolekweb.wrjug.r2dbc;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
