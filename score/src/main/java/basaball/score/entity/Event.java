package basaball.score.entity;

import lombok.Data;

@Data
public class Event {
  private int id;
  private int gameId;
  private int teamId;
  private int inning;
  private int atBatId;
  private int resultFirstRunnerId;
  private int resultSecondRunnerId;
  private int resultThirdRunnerId;
  private int resultOutCount;
  private int timing;
}
