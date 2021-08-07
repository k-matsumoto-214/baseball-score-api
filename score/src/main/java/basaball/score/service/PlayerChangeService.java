package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.PlayerChangeDao;
import basaball.score.entity.PlayerChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerChangeService {
  @Autowired
  private PlayerChangeDao playerChangeDao;

  public void create(PlayerChange playerChange) throws RegistrationException {
    if (playerChangeDao.create(playerChange) != 1) {
      throw new RegistrationException("選手交代登録に失敗しました。");
    }
  }
}
