package basaball.score.form;

import lombok.Data;

@Data
public class ErrorForm {
  private Integer id;
  private int teamId;
  private int eventId;
  private int playerId;
}
