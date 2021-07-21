package basaball.score.service;

import basaball.score.dao.PlayersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  @Autowired
  private PlayersDao playersDao;
}
