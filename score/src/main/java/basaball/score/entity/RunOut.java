package basaball.score.entity;

import lombok.Data;

@Data
public class RunOut {
  private int id;
  private int playerId;
  private int teamId;
  private int eventId;
}
