package basaball.score.dao;

import basaball.score.entity.Player;
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
public class PlayersDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Player player) {
    String sql = "insert into players values (null, :teamId, :name, :number, :birthday, :position, :image, :comment, false)";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", player.getTeamId())
                                        .addValue("name", player.getName())
                                        .addValue("number", player.getNumber())
                                        .addValue("birthday", player.getBirthday())
                                        .addValue("position", player.getPosition())
                                        .addValue("image", player.getImage())
                                        .addValue("comment", player.getComment());

    return jdbcTemplate.update(sql, parameters);
  }

  public Player findById(int playerId, int teamId) {
    String sql = "select * from players where id = :playerId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("playerId", playerId)
                                        .addValue("teamId", teamId);

    RowMapper<Player> rowMapper = new BeanPropertyRowMapper<Player>(Player.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public Player findByIdOnly(int playerId) {
    String sql = "select * from players where id = :playerId";

    SqlParameterSource parameters = new MapSqlParameterSource("playerId", playerId);

    RowMapper<Player> rowMapper = new BeanPropertyRowMapper<Player>(Player.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<Player> findByTeamId(int teamId) {
    String sql = "select * from players where team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("teamId", teamId);

    RowMapper<Player> rowMapper = new BeanPropertyRowMapper<Player>(Player.class);

    List<Player> resultList = jdbcTemplate.query(sql, parameters, rowMapper);
    if (resultList.size() == 0) {
      return null;
    } else {
      return resultList;
    }
  }

  public void selectForUpdate(int playerId) {
    String sql = "select * from players where id = :playerId for update";

    SqlParameterSource parameters = new MapSqlParameterSource("playerId", playerId);

    jdbcTemplate.queryForMap(sql, parameters);
  }

  public int update(Player player) {
    String sql = "update players set name = :name, number = :number, birthday = :birthday, "
                 + "position = :position, image = :image, comment = :comment "
                 + "where id = :id and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("name", player.getName())
                                        .addValue("number", player.getNumber())
                                        .addValue("birthday", player.getBirthday())
                                        .addValue("position", player.getPosition())
                                        .addValue("image", player.getImage())
                                        .addValue("comment", player.getComment())
                                        .addValue("id", player.getId())
                                        .addValue("teamId", player.getTeamId());

    return jdbcTemplate.update(sql, parameters);
  }

  public int delete(int playerId, int teamId) {
    String sql = "update players set delete_flg = true where id = :playerId and team_id = :teamId";

    SqlParameterSource parameters = new MapSqlParameterSource("playerId", playerId)
                                        .addValue("teamId", teamId);

    return jdbcTemplate.update(sql, parameters);
  }
}
