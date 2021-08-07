package basaball.score.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Game {
  private int id;
  private int teamId;
  private String opponentTeam;
  private Integer topScore;
  private Integer bottomScore;
  private Date date;
  private String field;
  private Integer result;
  private boolean topFlg;
  private boolean resultFlg;
  private int lineupingStatus;
  private String topLineup;
  private String bottomLineup;
  private Integer inning;
  private Integer winningPitcher;
  private Integer losingPitcher;
  private Integer savePitcher;
  private String comment;
}
