package basaball.score.dao;

import basaball.score.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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

  public Error findByEventId(int eventId, int teamId) {
    String sql = "select * from errors where event_id = :eventId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId)
                                        .addValue("teamId", teamId);

    RowMapper<Error> rowMapper = new BeanPropertyRowMapper<Error>(Error.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from errors where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }
}
