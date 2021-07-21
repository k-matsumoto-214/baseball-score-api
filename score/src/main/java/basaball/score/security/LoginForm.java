package basaball.score.security;

import lombok.Data;

@Data
public class LoginForm {
  private String accountId;
  private String password;
}
