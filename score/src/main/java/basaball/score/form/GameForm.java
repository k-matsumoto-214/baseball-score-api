package basaball.score.form;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class GameForm {
  @Min(1)
  private int id;
  @Min(1)
  private int teamId;
  @Size(min = 1, max = 100)
  @NotBlank
  private String opponentTeam;
  private Integer topScore;
  private Integer bottomScore;
  private Date date;
  @Size(max = 100)
  private String field;
  @Min(0)
  @Max(2)
  private Integer result;
  private boolean topFlg;
  private boolean resultFlg;
  @Min(0)
  @Max(2)
  private int lineupingStatus;
  private List<LineupForm> topLineup;
  private List<LineupForm> bottomLineup;
  private Integer inning;
  private Integer winningPitcher;
  private Integer losingPitcher;
  private Integer savePitcher;
  private String comment;
}
