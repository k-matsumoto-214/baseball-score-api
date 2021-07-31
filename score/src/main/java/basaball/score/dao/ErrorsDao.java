package basaball.score.dao;

import basaball.score.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ErrorsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Error error) {
    String sql = "insert into errors values (null, :teamId, :eventId, :playerId)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", error.getTeamId())
                                        .addValue("eventId", error.getEventId())
                                        .addValue("playerId", error.getPlayerId());

    return jdbcTemplate.update(sql, parameters);
  }
}
