package basaball.score.entity;

import lombok.Data;

@Data
public class Error {
  private int id;
  private int teamId;
  private int eventId;
  private int playerId;
}
