package basaball.score.dao;

import basaball.score.entity.PlayerChange;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerChangeDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(PlayerChange playerChange) {
    String sql = "insert into player_changes values (null, :teamId, :gameId, :atBatId, :outPlayerId, :inPlayerId, :beforeField, :afterField, :changeStatus, :eventId)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", playerChange.getTeamId())
                                        .addValue("gameId", playerChange.getGameId())
                                        .addValue("atBatId", playerChange.getAtBatId())
                                        .addValue("outPlayerId", playerChange.getOutPlayerId())
                                        .addValue("inPlayerId", playerChange.getInPlayerId())
                                        .addValue("beforeField", playerChange.getBeforeField())
                                        .addValue("afterField", playerChange.getAfterField())
                                        .addValue("beforeField", playerChange.getBeforeField())
                                        .addValue("changeStatus", playerChange.getChangeStatus())
                                        .addValue("eventId", playerChange.getEventId());

    return jdbcTemplate.update(sql, parameters);
  }

  public List<PlayerChange> findByEventId(int eventId, int teamId) {
    String sql = "select * from player_changes where event_id = :eventId and team_id = :teamId";
    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId)
                                        .addValue("eventId", eventId);

    RowMapper<PlayerChange> rowMapper = new BeanPropertyRowMapper<PlayerChange>(PlayerChange.class);

    List<PlayerChange> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int deleteByEventId(int eventId) {
    String sql = "delete from player_changes where event_id = :eventId";

    SqlParameterSource parameters = new MapSqlParameterSource("eventId", eventId);

    return jdbcTemplate.update(sql, parameters);
  }
}
