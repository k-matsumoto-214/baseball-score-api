package basaball.score.form;

import lombok.Data;

@Data
public class RunForm {
  private Integer id;
  private int teamId;
  private int gameId;
  private int eventId;
  private int atBatId;
  private int batterId;
  private int pitcherId;
  private int runnerId;
  private int inning;
  private boolean earnedFlg;
  private boolean rbiFlg;
  private boolean topFlg;
}
