package basaball.score.dao;

import basaball.score.entity.BatteryError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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

  public BatteryError findByEventId(int eventId, int teamId) {
    String sql = "select * from battery_errors where event_id = :eventId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId)
                                        .addValue("teamId", teamId);

    RowMapper<BatteryError> rowMapper = new BeanPropertyRowMapper<BatteryError>(BatteryError.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from battery_errors where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }
}
