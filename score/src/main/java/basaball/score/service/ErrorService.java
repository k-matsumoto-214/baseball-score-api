package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.ErrorsDao;
import basaball.score.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {
  @Autowired
  private ErrorsDao errorsDao;
  public void create(Error error) throws RegistrationException {
    if (errorsDao.create(error) != 1) {
      throw new RegistrationException("エラー登録に失敗しました。");
    }
  }
}
