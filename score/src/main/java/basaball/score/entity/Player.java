package basaball.score.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Player {
  private int id;
  private int teamId;
  private String name;
  private int number;
  private Date birthday;
  private String position;
  private String image;
  private String comment;
}
