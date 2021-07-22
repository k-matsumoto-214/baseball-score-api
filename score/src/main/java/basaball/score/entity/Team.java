package basaball.score.entity;

import lombok.Data;

@Data
public class Team {
  private int id;
  private String name;
  private String image;
  private String comment;
  private String accountId;
  private String password;
}
