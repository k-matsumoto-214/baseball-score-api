package basaball.score.form;

import lombok.Data;

@Data
public class EventForm {
  private Integer id;
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
