package basaball.score.form;

import java.util.List;
import lombok.Data;

@Data
public class LineupForm {
  private int orderNumber;
  private List<orderDetail> orderDetails;

  @Data
  public static class orderDetail {
    private int playerId;
    private int fieldNumber;
  }
}
