package basaball.score.form;

import javax.validation.constraints.Max;
import lombok.Data;

@Data
public class AtBatForm {
  private Integer id;
  private int teamId;
  private int gameId;
  private int batterId;
  private int pitcherId;
  private int inning;
  @Max(2)
  private int outCount;
  private Integer firstRunnerId;
  private Integer secondRunnerId;
  private Integer thirdRunnerId;
  private boolean playerChangeFlg;
  private Integer direction;
  private Boolean completeFlg;
  private String comment;
  private Integer result;
  private int lineupNumber;
  private boolean topFlg;
}
