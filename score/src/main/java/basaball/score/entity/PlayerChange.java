package basaball.score.entity;

import lombok.Data;

@Data
public class PlayerChange {
  private int id;
  private int teamId;
  private int gameId;
  private int atBatId;
  private Integer outPlayerId;
  private Integer inPlayerId;
  private int changeStatus;
  private int beforeField;
  private int afterField;
  private int eventId;
}
