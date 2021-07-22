package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.DeleteException;
import basaball.score.controller.exception.DuplicateAccountIdException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.TeamsDao;
import basaball.score.entity.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamsService {
  @Autowired
  private TeamsDao teamsDao;
  @Autowired
  private BCryptPasswordEncoder encoder;

  public void create(Team team) throws RegistrationException, DuplicateAccountIdException {
    team.setPassword(encoder.encode(team.getPassword()));
    if (teamsDao.findByAccountId(team.getAccountId()) != 0) {
      throw new DuplicateAccountIdException("アカウントIDが重複しています。");
    }
    if (teamsDao.create(team) != 1) {
      throw new RegistrationException("チーム登録に失敗しました。");
    }
  }

  public Map<String, Object> findById(int id) throws DataNotFoundException {
    Team team = teamsDao.findById(id);
    if (team == null) {
      throw new DataNotFoundException("チームが見つかりません。");
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("name", team.getName());
    result.put("comment", team.getComment());
    result.put("image", team.getImage());

    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(Team team) throws UpdateException {
    teamsDao.selectForUpdate(team.getId());
    if (teamsDao.update(team) != 1) {
      throw new UpdateException("チーム更新に失敗しました。");
    }
  }

  public void delete(int id) throws DeleteException {
    if (teamsDao.delete(id) != 1) {
      throw new DeleteException("チームの削除に失敗しました。");
    }
  }
}
