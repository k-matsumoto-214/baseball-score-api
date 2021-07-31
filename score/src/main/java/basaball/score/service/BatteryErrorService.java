package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.BatteryErrorsDao;
import basaball.score.entity.BatteryError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatteryErrorService {
  @Autowired
  private BatteryErrorsDao batteryErrorsDao;
  public void create(BatteryError batteryError) throws RegistrationException {
    if (batteryErrorsDao.create(batteryError) != 1) {
      throw new RegistrationException("バッテリーエラー登録に失敗しました。");
    }
  }
}
