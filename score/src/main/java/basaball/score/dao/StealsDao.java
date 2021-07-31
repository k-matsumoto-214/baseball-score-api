package basaball.score.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import basaball.score.entity.Steal;

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
                                        .addValue("successFlg", steal.getSuccessFlg());

    return jdbcTemplate.update(sql, parameters);
  }
}
