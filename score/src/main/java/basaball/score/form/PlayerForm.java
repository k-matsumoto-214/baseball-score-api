package basaball.score.form;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerForm {
  @Min(1)
  private int teamId;
  @Size(min = 1, max = 100)
  @NotBlank
  private String name;
  private Integer number;
  private Date birthday;
  @Size(max = 100)
  private String position;
  private String image;
  @Size(max = 300)
  private String comment;
}
