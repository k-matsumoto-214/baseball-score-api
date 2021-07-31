package basaball.score.dao;

import basaball.score.entity.RunOut;
import org.springframework.beans.factory.annotation.Autowired;
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
}
