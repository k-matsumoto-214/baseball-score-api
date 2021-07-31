package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.SpecialsDao;
import basaball.score.entity.Special;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialService {
  @Autowired
  SpecialsDao specialsDao;

  public void create(Special special) throws RegistrationException {
    if (specialsDao.create(special) != 1) {
      throw new RegistrationException("特殊登録に失敗しました。");
    }
  }
}
