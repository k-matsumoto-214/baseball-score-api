package basaball.score.dao;

import basaball.score.entity.Special;
import org.springframework.beans.factory.annotation.Autowired;
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
}
