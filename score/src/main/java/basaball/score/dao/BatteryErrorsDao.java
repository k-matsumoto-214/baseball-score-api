package basaball.score.dao;

import basaball.score.entity.BatteryError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class BatteryErrorsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(BatteryError batteryError) {
    String sql = "insert into battery_errors values (null, :eventId, :teamId, :pitcherId, :catcherId, :wpFlg)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", batteryError.getTeamId())
                                        .addValue("eventId", batteryError.getEventId())
                                        .addValue("pitcherId", batteryError.getPitcherId())
                                        .addValue("catcherId", batteryError.getCatcherId())
                                        .addValue("wpFlg", batteryError.isWpFlg());

    return jdbcTemplate.update(sql, parameters);
  }
}
