package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.AtBatsDao;
import basaball.score.dao.BatteryErrorsDao;
import basaball.score.dao.ErrorsDao;
import basaball.score.dao.EventsDao;
import basaball.score.dao.GamesDao;
import basaball.score.dao.PlayerChangeDao;
import basaball.score.dao.RunOutsDao;
import basaball.score.dao.RunsDao;
import basaball.score.dao.SpecialsDao;
import basaball.score.dao.StealsDao;
import basaball.score.entity.AtBat;
import basaball.score.entity.Event;
import basaball.score.entity.Game;
import basaball.score.entity.PlayerChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
  @Autowired
  private RunsDao runsDao;
  @Autowired
  private RunOutsDao runOutsDao;
  @Autowired
  private EventsDao eventsDao;
  @Autowired
  private BatteryErrorsDao batteryErrorsDao;
  @Autowired
  private ErrorsDao errorsDao;
  @Autowired
  private StealsDao stealsDao;
  @Autowired
  private SpecialsDao specialsDao;
  @Autowired
  private PlayerChangeDao playerChangeDao;
  @Autowired
  private GamesDao gamesDao;

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

  @Transactional(rollbackFor = Exception.class)
  public void deleteAtBat(AtBat beforeAtBat, AtBat atBat) throws UpdateException {
    List<Event> beforeEvents = eventsDao.findByAtBatId(beforeAtBat.getId(), beforeAtBat.getTeamId());
    if (beforeEvents != null) {
      for (Event event : beforeEvents) {
        try {
          if (event.getEventType() != null) {
            switch (event.getEventType()) {
              case 0:
                stealsDao.deleteByEventId(event.getId());
                break;
              case 1:
                batteryErrorsDao.deleteByEventId(event.getId());
                break;
              case 2:
                errorsDao.deleteByEventId(event.getId());
                break;
              case 3:
                specialsDao.deleteByEventId(event.getId());
                break;
              case 4:
                // オーダーのリセット
                Game target = gamesDao.findById(event.getGameId(), event.getTeamId());
                JsonArray targetLineups = target.isTopFlg()
                                              ? JsonParser.parseString(target.getTopLineup()).getAsJsonArray()
                                              : JsonParser.parseString(target.getBottomLineup()).getAsJsonArray();

                for (int i = 0; i < targetLineups.size(); i++) {
                  int deleteIdx = (targetLineups.get(i).getAsJsonObject().get("orderDetails").getAsJsonArray().size()) - 1;
                  targetLineups.get(i).getAsJsonObject().get("orderDetails").getAsJsonArray().remove(deleteIdx);
                }

                if (target.isTopFlg()) {
                  target.setTopLineup(targetLineups.toString());
                } else {
                  target.setBottomLineup(targetLineups.toString());
                }
                gamesDao.selectForUpdate(event.getGameId());
                gamesDao.update(target);

                List<PlayerChange> playerChanges = playerChangeDao.findByEventId(event.getId(), event.getTeamId());
                // 選手交代イベントが投手交代、代打の場合は打席情報のリセット
                for (PlayerChange playerChange : playerChanges) {
                  if (playerChange.getChangeStatus() == 2) {
                    beforeAtBat.setBatterId(playerChange.getOutPlayerId());
                    break;
                  }
                  if (playerChange.getChangeStatus() == 0) {
                    for (JsonElement targetLineup : targetLineups) {
                      int lastIdx = (targetLineup.getAsJsonObject().get("orderDetails").getAsJsonArray().size()) - 1;
                      int fieldNumber = targetLineup.getAsJsonObject().get("orderDetails").getAsJsonArray().get(lastIdx).getAsJsonObject().get("fieldNumber").getAsInt();
                      if (fieldNumber == 1) {
                        beforeAtBat.setPitcherId(targetLineup.getAsJsonObject().get("orderDetails").getAsJsonArray().get(lastIdx).getAsJsonObject().get("playerId").getAsInt());
                        break;
                      }
                    }
                    break;
                  }
                }

                // 選手交代イベントの削除
                playerChangeDao.deleteByEventId(event.getId());
                break;
            }
          }
          runsDao.deleteByEventId(event.getId());
          runOutsDao.deleteByEventId(event.getId());
          eventsDao.delete(event.getId());
        } catch (Exception e) {
          e.printStackTrace();
          throw new UpdateException("イベントID：" + event.getId() + "の処理中にエラーが発生しました。");
        }
      }
    }

    List<Event> allBattingEvents = eventsDao.findBattingEventByGameId(beforeAtBat.getGameId(), beforeAtBat.getTeamId());
    if (allBattingEvents != null) {
      Event beforeBeforeEvent = allBattingEvents.get(allBattingEvents.size() - 1);
      if (beforeBeforeEvent.getResultOutCount() != 3) {
        beforeAtBat.setFirstRunnerId(beforeBeforeEvent.getResultFirstRunnerId());
        beforeAtBat.setSecondRunnerId(beforeBeforeEvent.getResultSecondRunnerId());
        beforeAtBat.setThirdRunnerId(beforeBeforeEvent.getResultThirdRunnerId());
      } else {
        beforeAtBat.setFirstRunnerId(null);
        beforeAtBat.setSecondRunnerId(null);
        beforeAtBat.setThirdRunnerId(null);
      }
    } else {
      beforeAtBat.setFirstRunnerId(null);
      beforeAtBat.setSecondRunnerId(null);
      beforeAtBat.setThirdRunnerId(null);
    }

    atBatsDao.selectForUpdate(beforeAtBat.getId());
    if (atBatsDao.update(beforeAtBat) != 1) {
      throw new UpdateException("前打席の初期化更新に失敗しました。");
    }

    List<Event> events = eventsDao.findByAtBatId(atBat.getId(), atBat.getTeamId());
    if (events != null) {
      for (Event event : events) {
        try {
          switch (event.getEventType()) {
            case 0:
              stealsDao.deleteByEventId(event.getId());
              break;
            case 1:
              batteryErrorsDao.deleteByEventId(event.getId());
              break;
            case 2:
              errorsDao.deleteByEventId(event.getId());
              break;
            case 3:
              specialsDao.deleteByEventId(event.getId());
              break;
          }
          runsDao.deleteByEventId(event.getId());
          runOutsDao.deleteByEventId(event.getId());
          eventsDao.delete(event.getId());
        } catch (Exception e) {
          throw new UpdateException("イベントID：" + event.getId() + "の処理中にエラーが発生しました。");
        }
      }
    }

    if (atBatsDao.delete(atBat.getId(), atBat.getTeamId()) != 1) {
      throw new UpdateException("現打席の削除に失敗しました。");
    }
  }
}
