package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.AtBatsDao;
import basaball.score.dao.RunsDao;
import basaball.score.entity.AtBat;
import basaball.score.entity.Run;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
  @Autowired
  RunsDao runsDao;
  @Autowired
  AtBatsDao atBatsDao;

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

  public Map<String, Object> findByGameId(int gameId, int teamId) {
    Map<String, Object> result = new LinkedHashMap<>();
    List<Map<String, Object>> topRuns = runsDao.findByGameId(gameId, teamId, true);
    List<Map<String, Object>> bottomRuns = runsDao.findByGameId(gameId, teamId, false);
    List<AtBat> atBats = atBatsDao.findByGameId(gameId);
    int inning = atBats.get(atBats.size() - 1).getInning();
    inning = inning > 7 ? inning : 7;

    int topScore = 0;
    int bottomScore = 0;
    List<Map<String, Object>> topScores = new ArrayList<>(Collections.nCopies(inning, null));
    List<Map<String, Object>> bottomScores = new ArrayList<>(Collections.nCopies(inning, null));

    if (topRuns != null) {
      for (Map<String, Object> topRun : topRuns) {
        Map<String, Object> tempMap = new LinkedHashMap<>();
        topScore += Integer.parseInt(topRun.get("score").toString());
        int targetInning = Integer.parseInt(topRun.get("inning").toString());
        tempMap.put("inning", targetInning);
        tempMap.put("score", topRun.get("score"));
        topScores.set(targetInning - 1, tempMap);
      }
    }

    if (bottomRuns != null) {
      for (Map<String, Object> bottomRun : bottomRuns) {
        Map<String, Object> tempMap = new LinkedHashMap<>();
        bottomScore += Integer.parseInt(bottomRun.get("score").toString());
        int targetInning = Integer.parseInt(bottomRun.get("inning").toString());
        tempMap.put("inning", targetInning);
        tempMap.put("score", bottomRun.get("score"));
        bottomScores.set(targetInning - 1, tempMap);
      }
    }

    result.put("topScore", topScore);
    result.put("topScores", topScores);
    result.put("bottomScore", bottomScore);
    result.put("bottomScores", bottomScores);

    return result;
  }
}
