package basaball.score.dao;

import basaball.score.entity.Special;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Special special) {
    String sql = "insert into specials values (null, :eventId, :teamId)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", special.getTeamId())
                                        .addValue("eventId", special.getEventId());

    return jdbcTemplate.update(sql, parameters);
  }

  public Special findByEventId(int eventId, int teamId) {
    String sql = "select * from specials where event_id = :eventId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId)
                                        .addValue("teamId", teamId);

    RowMapper<Special> rowMapper = new BeanPropertyRowMapper<Special>(Special.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from specials where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }
}
