package basaball.score.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginTeamService implements UserDetailsService {
  @Autowired
  private LoginDao loginDao;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    LoginTeam team = loginDao.findByAccountId(accountId);
    if (team == null) {
      throw new UsernameNotFoundException("");
    }
    return team;
  }
}
