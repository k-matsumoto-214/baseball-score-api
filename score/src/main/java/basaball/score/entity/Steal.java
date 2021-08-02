package basaball.score.entity;

import lombok.Data;

@Data
public class Steal {
  private int id;
  private int eventId;
  private int teamId;
  private int runnerId;
  private int pitcherId;
  private int catcherId;
  private boolean successFlg;
}
