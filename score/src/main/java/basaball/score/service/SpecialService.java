package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.SpecialsDao;
import basaball.score.entity.Special;
import java.util.LinkedHashMap;
import java.util.Map;
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

  public Map<String, Object> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    Special special = specialsDao.findByEventId(eventId, teamId);
    if (special == null) {
      throw new DataNotFoundException("特殊情報が見つかりません。");
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", special.getId());
    result.put("teamId", special.getTeamId());

    return result;
  }
}
