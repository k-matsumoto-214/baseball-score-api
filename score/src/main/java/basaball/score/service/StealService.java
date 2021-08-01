package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.StealsDao;
import basaball.score.entity.Steal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StealService {
  @Autowired
  private StealsDao stealsDao;

  public void create(Steal steal) throws RegistrationException {
    if (stealsDao.create(steal) != 1) {
      throw new RegistrationException("盗塁の登録に失敗しました。");
    }
  }

  public List<Map<String, Object>> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    List<Steal> steals = stealsDao.findByEventId(eventId, teamId);
    if (steals == null) {
      throw new DataNotFoundException("盗塁情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (Steal steal : steals) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", steal.getId());
      tempMap.put("teamId", steal.getTeamId());
      tempMap.put("runnerId", steal.getRunnerId());
      tempMap.put("successFlg", steal.isSuccessFlg());

      result.add(tempMap);
    }

    return result;
  }
}
