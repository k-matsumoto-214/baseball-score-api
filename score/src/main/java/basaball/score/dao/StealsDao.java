package basaball.score.dao;

import basaball.score.entity.Steal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class StealsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Steal steal) {
    String sql = "insert into steals values (null, :eventId, :teamId, :runnerId, :pitcherId, :catcherId, :successFlg)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", steal.getTeamId())
                                        .addValue("eventId", steal.getEventId())
                                        .addValue("runnerId", steal.getRunnerId())
                                        .addValue("pitcherId", steal.getPitcherId())
                                        .addValue("catcherId", steal.getCatcherId())
                                        .addValue("successFlg", steal.isSuccessFlg());

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Steal> findByEventId(int eventId, int teamId) {
    String sql = "select * from steals where event_id = :eventId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("eventId", eventId);

    RowMapper<Steal> rowMapper = new BeanPropertyRowMapper<Steal>(Steal.class);

    List<Steal> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from steals where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Steal> findByRunnerIdAndEventId(int runnerId, int eventId) {
    String sql = "select * from steals where runner_id = :runnerId and event_id = :eventId";
    SqlParameterSource parameters = new MapSqlParameterSource("runnerId", runnerId)
                                        .addValue("eventId", eventId);

    RowMapper<Steal> rowMapper = new BeanPropertyRowMapper<Steal>(Steal.class);

    List<Steal> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }
}
