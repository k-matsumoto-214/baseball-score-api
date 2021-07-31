package basaball.score.form;

import lombok.Data;

@Data
public class BatteryErrorForm {
  private Integer id;
  private int teamId;
  private int eventId;
  private int pitcherId;
  private int catcherId;
  private boolean wpFlg;
}
