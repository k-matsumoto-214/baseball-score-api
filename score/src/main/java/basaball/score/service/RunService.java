package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.RunsDao;
import basaball.score.entity.Run;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

  public List<Map<String, Object>> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    List<Run> runs = runsDao.findByEventId(eventId, teamId);
    if (runs == null) {
      throw new DataNotFoundException("得点情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (Run run : runs) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", run.getId());
      tempMap.put("teamId", run.getTeamId());
      tempMap.put("runnerId", run.getRunnerId());

      result.add(tempMap);
    }

    return result;
  }
}
