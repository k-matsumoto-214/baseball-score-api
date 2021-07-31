package basaball.score.dao;

import basaball.score.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class EventsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int fetchLastInsertId() {
    String sql = "select last_insert_id()";
    SqlParameterSource parameters = new MapSqlParameterSource();
    return jdbcTemplate.queryForObject(sql, parameters, Integer.class);
  }

  public int create(Event event) {
    String sql = "insert into events values (null, :gameId, :teamId, :inning, :atBatId, "
                 + ":resultFirstRunnerId, :resultSecondRunnerId, :resultThirdRunnerId, :resultOutCount, :timing, :eventType, :comment)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", event.getTeamId())
                                        .addValue("gameId", event.getGameId())
                                        .addValue("inning", event.getInning())
                                        .addValue("atBatId", event.getAtBatId())
                                        .addValue("resultFirstRunnerId", event.getResultFirstRunnerId())
                                        .addValue("resultSecondRunnerId", event.getResultSecondRunnerId())
                                        .addValue("resultThirdRunnerId", event.getResultThirdRunnerId())
                                        .addValue("resultOutCount", event.getResultOutCount())
                                        .addValue("timing", event.getTiming())
                                        .addValue("eventType", event.getEventType())
                                        .addValue("comment", event.getComment());

    return jdbcTemplate.update(sql, parameters);
  }
}
