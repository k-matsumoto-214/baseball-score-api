package basaball.score.dao;

import basaball.score.entity.Event;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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

  public List<Event> findByAtBatId(int atBatId, int teamId) {
    String sql = "select * from events where at_bat_id = :atBatId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("atBatId", atBatId);

    RowMapper<Event> rowMapper = new BeanPropertyRowMapper<Event>(Event.class);

    List<Event> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int delete(int id) {
    String sql = "delete from events where id = :id";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id);

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Event> findBattingEventByGameId(int gameId, int teamId) {
    String sql = "select * from events where game_id = :gameId and team_id = :teamId and timing = 1";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("gameId", gameId);

    RowMapper<Event> rowMapper = new BeanPropertyRowMapper<Event>(Event.class);

    List<Event> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public List<Event> findByGameId(int gameId, int teamId) {
    String sql = "select * from events where game_id = :gameId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("gameId", gameId);

    RowMapper<Event> rowMapper = new BeanPropertyRowMapper<Event>(Event.class);

    List<Event> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }
}
