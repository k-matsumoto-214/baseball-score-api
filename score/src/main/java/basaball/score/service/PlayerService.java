package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.DeleteException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.PlayersDao;
import basaball.score.entity.Player;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
  @Autowired
  private PlayersDao playersDao;

  public void create(Player player) throws RegistrationException {
    if (playersDao.create(player) != 1) {
      throw new RegistrationException("プレイヤー登録に失敗しました。");
    }
  }

  public Map<String, Object> findById(int playerId, int teamId) throws DataNotFoundException {
    Player player = playersDao.findById(playerId, teamId);
    if (player == null) {
      throw new DataNotFoundException("プレイヤーが見つかりません。");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", player.getId());
    result.put("name", player.getName());
    result.put("number", player.getNumber());
    result.put("birthday", player.getBirthday() != null ? sdf.format(player.getBirthday()) : null);
    result.put("position", player.getPosition());
    result.put("image", player.getImage());
    result.put("comment", player.getComment());
    result.put("deleteFlg", player.isDeleteFlg());

    return result;
  }

  public List<Map<String, Object>> findByTeamId(int teamId) throws DataNotFoundException {
    List<Player> players = playersDao.findByTeamId(teamId);
    if (players == null) {
      throw new DataNotFoundException("プレイヤーが見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (Player player : players) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", player.getId());
      tempMap.put("name", player.getName());
      tempMap.put("number", player.getNumber());
      tempMap.put("birthday", player.getBirthday() != null ? sdf.format(player.getBirthday()) : null);
      tempMap.put("position", player.getPosition());
      tempMap.put("image", player.getImage());
      tempMap.put("comment", player.getComment());
      tempMap.put("deleteFlg", player.isDeleteFlg());
      result.add(tempMap);
    }

    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(Player player) throws UpdateException {
    playersDao.selectForUpdate(player.getId());
    if (playersDao.update(player) != 1) {
      throw new UpdateException("プレイヤー更新に失敗しました。");
    }
  }

  public void delete(int playerId, int teamId) throws DeleteException {
    if (playersDao.delete(playerId, teamId) != 1) {
      throw new DeleteException("プレイヤーの削除に失敗しました。");
    }
  }
}
