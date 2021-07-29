package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.RunsDao;
import basaball.score.entity.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
  @Autowired
  RunsDao runsDao;

  public void create(Run run) throws RegistrationException {
    if (runsDao.create(run) != 1) {
      throw new RegistrationException("得点登録に失敗しました。");
    }
  }
}
