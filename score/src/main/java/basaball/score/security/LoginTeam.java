package basaball.score.security;

import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class LoginTeam implements UserDetails {
  private int id;
  private String name;
  private String accountId;
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
  @Override
  public String getUsername() {
    return accountId;
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }
}
