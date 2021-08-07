package basaball.score.dao;

import basaball.score.entity.PlayerChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerChangeDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(PlayerChange playerChange) {
    String sql = "insert into player_changes values (null, :teamId, :gameId, :atBatId, :outPlayerId, :inPlayerId, :beforeField, :afterField, :changeStatus)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", playerChange.getTeamId())
                                        .addValue("gameId", playerChange.getGameId())
                                        .addValue("atBatId", playerChange.getAtBatId())
                                        .addValue("outPlayerId", playerChange.getOutPlayerId())
                                        .addValue("inPlayerId", playerChange.getInPlayerId())
                                        .addValue("beforeField", playerChange.getBeforeField())
                                        .addValue("afterField", playerChange.getAfterField())
                                        .addValue("beforeField", playerChange.getBeforeField())
                                        .addValue("changeStatus", playerChange.getChangeStatus());

    return jdbcTemplate.update(sql, parameters);
  }
}
