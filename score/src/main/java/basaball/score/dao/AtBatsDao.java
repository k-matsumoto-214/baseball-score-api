package basaball.score.dao;

import basaball.score.entity.AtBat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class AtBatsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<AtBat> findByGameId(int gameId) {
    String sql = "select * from at_bats where game_id = :gameId";
    SqlParameterSource parameters = new MapSqlParameterSource("gameId", gameId);

    RowMapper<AtBat> rowMapper = new BeanPropertyRowMapper<AtBat>(AtBat.class);

    List<AtBat> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public int create(AtBat atBat) {
    String sql = "insert into at_bats values (null, :teamId, :gameId, :batterId, :pitcherId, :inning, :outCount, "
                 + ":firstRunnerId, :secondRunnerId, :thirdRunnerId, :playerChangeFlg, :direction, :completeFlg, "
                 + ":comment, :result, :lineupNumber, :topFlg)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", atBat.getTeamId())
                                        .addValue("gameId", atBat.getGameId())
                                        .addValue("batterId", atBat.getBatterId())
                                        .addValue("pitcherId", atBat.getPitcherId())
                                        .addValue("inning", atBat.getInning())
                                        .addValue("outCount", atBat.getOutCount())
                                        .addValue("firstRunnerId", atBat.getFirstRunnerId())
                                        .addValue("secondRunnerId", atBat.getSecondRunnerId())
                                        .addValue("thirdRunnerId", atBat.getThirdRunnerId())
                                        .addValue("playerChangeFlg", atBat.isPlayerChangeFlg())
                                        .addValue("direction", atBat.getDirection())
                                        .addValue("completeFlg", atBat.getCompleteFlg())
                                        .addValue("comment", atBat.getComment())
                                        .addValue("result", atBat.getResult())
                                        .addValue("lineupNumber", atBat.getLineupNumber())
                                        .addValue("topFlg", atBat.isTopFlg());

    return jdbcTemplate.update(sql, parameters);
  }

  public int update(AtBat atBat) {
    String sql = "update at_bats set game_id = :gameId, batter_id = :batterId, pitcher_id = :pitcherId, "
                 + "inning = :inning, out_count = :outCount, first_runner_id = :firstRunnerId, second_runner_id = :secondRunnerId, "
                 + "third_runner_id = :thirdRunnerId, player_change_flg = :playerChangeFlg, direction = :direction, complete_flg = :completeFlg, "
                 + "comment = :comment, result = :result, lineup_number = :lineupNumber, top_flg = :topFlg "
                 + "where team_id = :teamId and id = :id";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", atBat.getTeamId())
                                        .addValue("id", atBat.getId())
                                        .addValue("gameId", atBat.getGameId())
                                        .addValue("batterId", atBat.getBatterId())
                                        .addValue("pitcherId", atBat.getPitcherId())
                                        .addValue("inning", atBat.getInning())
                                        .addValue("outCount", atBat.getOutCount())
                                        .addValue("firstRunnerId", atBat.getFirstRunnerId())
                                        .addValue("secondRunnerId", atBat.getSecondRunnerId())
                                        .addValue("thirdRunnerId", atBat.getThirdRunnerId())
                                        .addValue("playerChangeFlg", atBat.isPlayerChangeFlg())
                                        .addValue("direction", atBat.getDirection())
                                        .addValue("completeFlg", atBat.getCompleteFlg())
                                        .addValue("comment", atBat.getComment())
                                        .addValue("result", atBat.getResult())
                                        .addValue("lineupNumber", atBat.getLineupNumber())
                                        .addValue("topFlg", atBat.isTopFlg());

    return jdbcTemplate.update(sql, parameters);
  }

  public void selectForUpdate(int id) {
    String sql = "select * from at_bats where id = :id for update";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id);

    jdbcTemplate.queryForMap(sql, parameters);
  }

  public int delete(int id, int teamId) {
    String sql = "delete from at_bats where id = :id and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id)
                                        .addValue("teamId", teamId);

    return jdbcTemplate.update(sql, parameters);
  }

  public AtBat findById(int atBatId) {
    String sql = "select * from at_bats where id = :atBatId";
    SqlParameterSource parameters = new MapSqlParameterSource("atBatId", atBatId);

    RowMapper<AtBat> rowMapper = new BeanPropertyRowMapper<AtBat>(AtBat.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
