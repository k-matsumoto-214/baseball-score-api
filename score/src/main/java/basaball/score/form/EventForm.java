package basaball.score.form;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EventForm {
  private Integer id;
  private int gameId;
  private int teamId;
  private int inning;
  private int atBatId;
  private Integer resultFirstRunnerId;
  private Integer resultSecondRunnerId;
  private Integer resultThirdRunnerId;
  private int resultOutCount;
  private int timing;
  private Integer eventType;
  @Size(max=200)
  private String comment;
}
