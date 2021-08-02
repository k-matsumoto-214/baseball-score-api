package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.RunOutsDao;
import basaball.score.entity.RunOut;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

  public List<Map<String, Object>> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    List<RunOut> runOuts = runOutsDao.findByEventId(eventId, teamId);
    if (runOuts == null) {
      throw new DataNotFoundException("走塁死情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (RunOut runOut : runOuts) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", runOut.getId());
      tempMap.put("teamId", runOut.getTeamId());
      tempMap.put("runnerId", runOut.getPlayerId());

      result.add(tempMap);
    }

    return result;
  }
}
