package basaball.score.entity;

import lombok.Data;

@Data
public class AtBat {
  private int id;
  private int teamId;
  private int gameId;
  private int batterId;
  private int pitcherId;
  private int inning;
  private int outCount;
  private Integer firstRunnerId;
  private Integer secondRunnerId;
  private Integer thirdRunnerId;
  private boolean playerChangeFlg;
  private Integer direction;
  private Boolean completeFlg;
  private String comment;
  private Integer result;
  private int lineupNumber;
  private boolean topFlg;
}