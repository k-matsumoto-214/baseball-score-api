package basaball.score.dao;

import basaball.score.entity.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Run run) {
    String sql = "insert into runs values (null, :teamId, :gameId, :eventId, :atBatId, :batterId, "
                 + ":pitcherId, :runnerId, :inning, :earnedFlg, :rbiFlg)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", run.getTeamId())
                                        .addValue("gameId", run.getGameId())
                                        .addValue("evantId", run.getEventId())
                                        .addValue("atBatId", run.getAtBatId())
                                        .addValue("batterId", run.getBatterId())
                                        .addValue("pitcherId", run.getPitcherId())
                                        .addValue("runnerId", run.getRunnerId())
                                        .addValue("inning", run.getInning())
                                        .addValue("earnedFlg", run.isEarndFlg())
                                        .addValue("rbiFlg", run.isRbiFlg());

    return jdbcTemplate.update(sql, parameters);
  }
}
