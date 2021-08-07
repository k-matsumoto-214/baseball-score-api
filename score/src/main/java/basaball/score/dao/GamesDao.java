package basaball.score.dao;

import basaball.score.entity.Game;
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
public class GamesDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int fetchLastInsertId() {
    String sql = "select last_insert_id()";
    SqlParameterSource parameters = new MapSqlParameterSource();
    return jdbcTemplate.queryForObject(sql, parameters, Integer.class);
  }

  public int create(Game game) {
    String sql = "insert into games values (null, :teamId, :opponentTeam, null, null, :date, :field, null, :topFlg, false, 0, :topLineup, :bottomLineup, null, null, null, null, comment)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", game.getTeamId())
                                        .addValue("opponentTeam", game.getOpponentTeam())
                                        .addValue("date", game.getDate())
                                        .addValue("field", game.getField())
                                        .addValue("topFlg", game.isTopFlg())
                                        .addValue("topLineup", game.getTopLineup())
                                        .addValue("bottomLineup", game.getBottomLineup());

    return jdbcTemplate.update(sql, parameters);
  }

  public List<Game> findByTeamId(int teamId) {
    String sql = "select * from games where team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId);

    RowMapper<Game> rowMapper = new BeanPropertyRowMapper<Game>(Game.class);

    List<Game> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public void selectForUpdate(int gameId) {
    String sql = "select * from games where id = :gameId for update";

    SqlParameterSource parameters = new MapSqlParameterSource("gameId", gameId);

    jdbcTemplate.queryForMap(sql, parameters);
  }

  public int update(Game game) {
    String sql = "update games set opponent_team = :opponentTeam, "
                 + "top_score = :topScore, bottom_score = :bottomScore, "
                 + "date = :date, field = :field, result = :result, top_flg = :topFlg, result_flg = :resultFlg, lineuping_status = :lineupingStatus, "
                 + "top_lineup = :topLineup, bottom_lineup = :bottomLineup, inning = :inning, "
                 + "winning_pitcher = :winningPitcher, losing_pitcher = :losingPitcher, save_pitcher = :savePitcher, comment = :comment "
                 + "where id = :id and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", game.getTeamId())
                                        .addValue("opponentTeam", game.getOpponentTeam())
                                        .addValue("topScore", game.getTopScore())
                                        .addValue("bottomScore", game.getBottomScore())
                                        .addValue("date", game.getDate())
                                        .addValue("field", game.getField())
                                        .addValue("result", game.getResult())
                                        .addValue("topFlg", game.isTopFlg())
                                        .addValue("resultFlg", game.isResultFlg())
                                        .addValue("lineupingStatus", game.getLineupingStatus())
                                        .addValue("topLineup", game.getTopLineup())
                                        .addValue("bottomLineup", game.getBottomLineup())
                                        .addValue("inning", game.getInning())
                                        .addValue("winningPitcher", game.getWinningPitcher())
                                        .addValue("losingPitcher", game.getLosingPitcher())
                                        .addValue("savePitcher", game.getSavePitcher())
                                        .addValue("comment", game.getComment())
                                        .addValue("id", game.getId());

    return jdbcTemplate.update(sql, parameters);
  }

  public Game findById(int gameId, int teamId) {
    String sql = "select * from games where id = :gameId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("gameId", gameId)
                                        .addValue("teamId", teamId);

    RowMapper<Game> rowMapper = new BeanPropertyRowMapper<Game>(Game.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
