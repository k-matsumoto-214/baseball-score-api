package basaball.score.entity;

import lombok.Data;

@Data
public class Event {
  private int id;
  private int gameId;
  private int teamId;
  private int inning;
  private int atBatId;
  private Integer resultFirstRunnerId;
  private Integer resultSecondRunnerId;
  private Integer resultThirdRunnerId;
  private Integer resultOutCount;
  private int timing;
  private Integer eventType;
  private String comment;
}
