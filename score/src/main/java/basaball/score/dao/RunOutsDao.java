package basaball.score.dao;

import basaball.score.entity.RunOut;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunOutsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(RunOut runOut) {
    String sql = "insert into run_outs values (null, :playerId, :teamId, :eventId)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", runOut.getTeamId())
                                        .addValue("eventId", runOut.getEventId())
                                        .addValue("playerId", runOut.getPlayerId());

    return jdbcTemplate.update(sql, parameters);
  }

  public List<RunOut> findByEventId(int eventId, int teamId) {
    String sql = "select * from run_outs where event_id = :eventId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("eventId", eventId);

    RowMapper<RunOut> rowMapper = new BeanPropertyRowMapper<RunOut>(RunOut.class);

    List<RunOut> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from run_outs where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }
}
