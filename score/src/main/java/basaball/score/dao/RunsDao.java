package basaball.score.dao;

import basaball.score.entity.Run;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Run run) {
    String sql = "insert into runs values (null, :teamId, :gameId, :eventId, :atBatId, :batterId, "
                 + ":pitcherId, :runnerId, :inning, :earnedFlg, :rbiFlg, :topFlg)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", run.getTeamId())
                                        .addValue("gameId", run.getGameId())
                                        .addValue("eventId", run.getEventId())
                                        .addValue("atBatId", run.getAtBatId())
                                        .addValue("batterId", run.getBatterId())
                                        .addValue("pitcherId", run.getPitcherId())
                                        .addValue("runnerId", run.getRunnerId())
                                        .addValue("inning", run.getInning())
                                        .addValue("earnedFlg", run.isEarnedFlg())
                                        .addValue("rbiFlg", run.isRbiFlg())
                                        .addValue("topFlg", run.isTopFlg());

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Run> findByEventId(int eventId, int teamId) {
    String sql = "select * from runs where event_id = :eventId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("eventId", eventId);

    RowMapper<Run> rowMapper = new BeanPropertyRowMapper<Run>(Run.class);

    List<Run> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from runs where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Map<String, Object>> findByGameId(int gameId, int teamId, boolean topFlg) {
    String sql = "select inning, count(*) as score from runs where game_id = :gameId and team_id = :teamId and top_flg = :topFlg group by inning";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("gameId", gameId)
                                        .addValue("topFlg", topFlg);

    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, parameters);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public List<Run> findByBatterIdAndGameId(int batterId, int gameId) {
    String sql = "select * from runs where batter_id = :batterId and game_id = :gameId";
    SqlParameterSource parameters = new MapSqlParameterSource("batterId", batterId)
                                        .addValue("gameId", gameId);

    RowMapper<Run> rowMapper = new BeanPropertyRowMapper<Run>(Run.class);

    List<Run> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public List<Run> findByRunnerIdAndGameId(int runnerId, int gameId) {
    String sql = "select * from runs where runner_id = :runnerId and game_id = :gameId";
    SqlParameterSource parameters = new MapSqlParameterSource("runnerId", runnerId)
                                        .addValue("gameId", gameId);

    RowMapper<Run> rowMapper = new BeanPropertyRowMapper<Run>(Run.class);

    List<Run> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public List<Run> findByPitcherIdAndGameId(int pitcherId, int gameId) {
    String sql = "select * from runs where pitcher_id = :pitcherId and game_id = :gameId";
    SqlParameterSource parameters = new MapSqlParameterSource("pitcherId", pitcherId)
                                        .addValue("gameId", gameId);

    RowMapper<Run> rowMapper = new BeanPropertyRowMapper<Run>(Run.class);

    List<Run> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }
}
