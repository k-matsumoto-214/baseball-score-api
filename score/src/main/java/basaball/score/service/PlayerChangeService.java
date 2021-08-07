package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.PlayerChangeDao;
import basaball.score.entity.PlayerChange;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerChangeService {
  @Autowired
  private PlayerChangeDao playerChangeDao;

  public void create(PlayerChange playerChange) throws RegistrationException {
    if (playerChangeDao.create(playerChange) != 1) {
      throw new RegistrationException("選手交代登録に失敗しました。");
    }
  }

  public List<Map<String, Object>> findByEventId(int eventId, int teamId) throws DataNotFoundException {
    List<PlayerChange> playerChanges = playerChangeDao.findByEventId(eventId, teamId);
    if (playerChanges == null) {
      throw new DataNotFoundException("選手交代情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (PlayerChange playerChange : playerChanges) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", playerChange.getId());
      tempMap.put("teamId", playerChange.getTeamId());
      tempMap.put("gameId", playerChange.getGameId());
      tempMap.put("atBatId", playerChange.getAtBatId());
      tempMap.put("outPlayerId", playerChange.getOutPlayerId());
      tempMap.put("inPlayerId", playerChange.getInPlayerId());
      tempMap.put("beforeField", playerChange.getBeforeField());
      tempMap.put("afterField", playerChange.getAfterField());
      tempMap.put("changeStatus", playerChange.getChangeStatus());

      result.add(tempMap);
    }

    return result;
  }
}
