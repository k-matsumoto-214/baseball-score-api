package basaball.score.dao;

import basaball.score.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class TeamsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public int create(Team team) {
    String sql = "insert into teams values (null, :name, :comment, :image, :accountId, :password)";

    SqlParameterSource parameters = new MapSqlParameterSource("name", team.getName())
                                        .addValue("comment", team.getComment())
                                        .addValue("image", team.getImage())
                                        .addValue("accountId", team.getAccountId())
                                        .addValue("password", team.getPassword());
    return jdbcTemplate.update(sql, parameters);
  }

  public Team findById(int id) {
    String sql = "select * from teams where id = :id";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id);

    RowMapper<Team> rowMapper = new BeanPropertyRowMapper<Team>(Team.class);

    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public void selectForUpdate(int id) {
    String sql = "select * from teams where id = :id for update";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id);

    jdbcTemplate.queryForMap(sql, parameters);
  }

  public int update(Team team) {
    String sql = "update teams set name = :name, comment = :comment, image = :image "
                 + "where id = :id";

    SqlParameterSource parameters = new MapSqlParameterSource("name", team.getName())
                                        .addValue("comment", team.getComment())
                                        .addValue("image", team.getImage())
                                        .addValue("id", team.getId());

    return jdbcTemplate.update(sql, parameters);
  }

  public int delete(int id) {
    String sql = "delete from teams where id = :id";

    SqlParameterSource parameters = new MapSqlParameterSource("id", id);

    return jdbcTemplate.update(sql, parameters);
  }

  public int findByAccountId(String accountId) {
    String sql = "select count(*) from teams where account_id = :accountId";

    SqlParameterSource parameters = new MapSqlParameterSource("accountId", accountId);

    return jdbcTemplate.queryForObject(sql, parameters, Integer.class);
  }
}
