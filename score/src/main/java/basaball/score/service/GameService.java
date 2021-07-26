package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.GamesDao;
import basaball.score.entity.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
  @Autowired
  private GamesDao gamesDao;

  public Map<String, Object> create(Game game) throws RegistrationException {
    if (gamesDao.create(game) != 1) {
      throw new RegistrationException("試合登録に失敗しました。");
    }
    int id = gamesDao.fetchLastInsertId();
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", id);
    return result;
  }

  public List<Map<String, Object>> findByTeamId(int teamId) throws DataNotFoundException {
    List<Game> games = gamesDao.findByTeamId(teamId);
    if (games == null) {
      throw new DataNotFoundException("試合情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (Game game : games) {
      JsonArray topLineup = JsonParser.parseString(game.getTopLineup()).getAsJsonArray();
      JsonArray bottomLineup = JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", game.getId());
      tempMap.put("teamId", game.getTeamId());
      tempMap.put("opponentTeam", game.getOpponentTeam());
      tempMap.put("topScore", game.getTopScore());
      tempMap.put("bottomScore", game.getBottomScore());
      tempMap.put("date", game.getDate() != null ? sdf.format(game.getDate()) : null);
      tempMap.put("field", game.getField());
      tempMap.put("result", game.getResult());
      tempMap.put("topFlg", game.isTopFlg());
      tempMap.put("resultFlg", game.isResultFlg());
      tempMap.put("lineupingStatus", game.getLineupingStatus());
      tempMap.put("topLineup", topLineup);
      tempMap.put("bottomLineup", bottomLineup);
      result.add(tempMap);
    }

    return result;
  }

  public Map<String, Object> findById(int gameId, int teamId) throws DataNotFoundException {
    Game game = gamesDao.findById(gameId, teamId);
    if (game == null) {
      throw new DataNotFoundException("試合情報が見つかりません。");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    JsonArray topLineup = JsonParser.parseString(game.getTopLineup()).getAsJsonArray();
    JsonArray bottomLineup = JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", game.getId());
    result.put("teamId", game.getTeamId());
    result.put("opponentTeam", game.getOpponentTeam());
    result.put("topScore", game.getTopScore());
    result.put("bottomScore", game.getBottomScore());
    result.put("date", game.getDate() != null ? sdf.format(game.getDate()) : null);
    result.put("field", game.getField());
    result.put("result", game.getResult());
    result.put("topFlg", game.isTopFlg());
    result.put("resultFlg", game.isResultFlg());
    result.put("lineupingStatus", game.getLineupingStatus());
    result.put("topLineup", topLineup);
    result.put("bottomLineup", bottomLineup);

    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(Game game) throws UpdateException {
    gamesDao.selectForUpdate(game.getId());
    if (gamesDao.update(game) != 1) {
      throw new UpdateException("試合情報更新に失敗しました。");
    }
  }
}
