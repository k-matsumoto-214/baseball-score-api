package basaball.score.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.StealsDao;
import basaball.score.entity.Steal;

@Service
public class StealService {
  @Autowired
  private StealsDao stealsDao;

  public void create(Steal steal) throws RegistrationException {
    if (stealsDao.create(steal) != 1) {
      throw new RegistrationException("盗塁の登録に失敗しました。");
    }
  }
}
