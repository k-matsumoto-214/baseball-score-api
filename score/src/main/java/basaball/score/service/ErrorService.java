package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.ErrorsDao;
import basaball.score.entity.Error;
import java.util.LinkedHashMap;
import java.util.Map;
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

  public Map<String, Object> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    Error error = errorsDao.findByEventId(eventId, teamId);
    if (error == null) {
      throw new DataNotFoundException("エラー情報が見つかりません。");
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", error.getId());
    result.put("teamId", error.getTeamId());
    result.put("playerId", error.getPlayerId());

    return result;
  }
}
