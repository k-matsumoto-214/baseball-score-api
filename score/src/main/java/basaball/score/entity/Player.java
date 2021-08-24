package basaball.score.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Player {
  private int id;
  private int teamId;
  private String name;
  private Integer number;
  private Date birthday;
  private String position;
  private String image;
  private String comment;
  private boolean deleteFlg;
}
