package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.BatteryErrorsDao;
import basaball.score.entity.BatteryError;
import java.util.LinkedHashMap;
import java.util.Map;
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

  public Map<String, Object> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    BatteryError batteryError = batteryErrorsDao.findByEventId(eventId, teamId);
    if (batteryError == null) {
      throw new DataNotFoundException("バッテリーエラー情報が見つかりません。");
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", batteryError.getId());
    result.put("teamId", batteryError.getTeamId());
    result.put("pitcherId", batteryError.getPitcherId());
    result.put("catcherId", batteryError.getCatcherId());
    result.put("wpFlg", batteryError.isWpFlg());

    return result;
  }
}
