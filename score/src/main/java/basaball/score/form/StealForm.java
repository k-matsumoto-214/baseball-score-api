package basaball.score.form;

import lombok.Data;

@Data
public class StealForm {
  private Integer id;
  private int eventId;
  private int teamId;
  private int runnerId;
  private int pitcherId;
  private int catcherId;
  private Boolean successFlg;
}
