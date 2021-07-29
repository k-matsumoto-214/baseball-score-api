package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.RunOutsDao;
import basaball.score.entity.RunOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunOutService {
  @Autowired
  RunOutsDao runOutsDao;

  public void create(RunOut runOut) throws RegistrationException {
    if (runOutsDao.create(runOut) != 1) {
      throw new RegistrationException("走塁死登録に失敗しました。");
    }
  }
}
