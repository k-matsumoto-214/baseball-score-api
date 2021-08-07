package basaball.score.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class PlayerChangeForm {
  private int teamId;
  private int gameId;
  private int atBatId;
  private Integer outPlayerId;
  private Integer inPlayerId;
  private int changeStatus;
  private int beforeField;
  @Min(0)
  @Max(3)
  private int afterField;
  private int eventId;
}
