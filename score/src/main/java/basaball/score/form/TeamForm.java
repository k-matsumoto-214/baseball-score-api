package basaball.score.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TeamForm {
  @Size(min = 8, max = 100)
  private String accountId;
  @Size(min = 8, max = 100)
  private String password;
  @Size(min = 1, max = 100)
  @NotBlank
  private String name;
  @Size(max = 300)
  private String comment;
  private String image;
}
