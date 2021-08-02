package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.AtBatsDao;
import basaball.score.entity.AtBat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtBatService {
  @Autowired
  private AtBatsDao atBatsDao;

  public List<Map<String, Object>> findByGameId(int gameId) throws DataNotFoundException {
    List<AtBat> atBats = atBatsDao.findByGameId(gameId);
    if (atBats == null) {
      throw new DataNotFoundException("打席情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (AtBat atBat : atBats) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", atBat.getId());
      tempMap.put("teamId", atBat.getTeamId());
      tempMap.put("gameId", atBat.getGameId());
      tempMap.put("batterId", atBat.getBatterId());
      tempMap.put("pitcherId", atBat.getPitcherId());
      tempMap.put("inning", atBat.getInning());
      tempMap.put("outCount", atBat.getOutCount());
      tempMap.put("firstRunnerId", atBat.getFirstRunnerId());
      tempMap.put("secondRunnerId", atBat.getSecondRunnerId());
      tempMap.put("thirdRunnerId", atBat.getThirdRunnerId());
      tempMap.put("playerChangeFlg", atBat.isPlayerChangeFlg());
      tempMap.put("direction", atBat.getDirection());
      tempMap.put("completeFlg", atBat.getCompleteFlg());
      tempMap.put("comment", atBat.getComment());
      tempMap.put("result", atBat.getResult());
      tempMap.put("lineupNumber", atBat.getLineupNumber());
      tempMap.put("topFlg", atBat.isTopFlg());
      result.add(tempMap);
    }

    return result;
  }

  public void create(AtBat atBat) throws RegistrationException {
    if (atBatsDao.create(atBat) != 1) {
      throw new RegistrationException("打席登録に失敗しました。");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(AtBat atBat) throws UpdateException {
    atBatsDao.selectForUpdate(atBat.getId());
    if (atBatsDao.update(atBat) != 1) {
      throw new UpdateException("打席更新に失敗しました。");
    }
  }
}
