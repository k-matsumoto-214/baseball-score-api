package basaball.score.entity;

import lombok.Data;

@Data
public class BatteryError {
  private int id;
  private int teamId;
  private int eventId;
  private int pitcherId;
  private int catcherId;
  private boolean wpFlg;
}
