package basaball.score.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  public LoginTeam findByAccountId(String accountId) {
    String sql = "SELECT * FROM teams WHERE account_id = :accountId";
    SqlParameterSource parameters = new MapSqlParameterSource("accountId", accountId);
    RowMapper<LoginTeam> rowMapper = new BeanPropertyRowMapper<LoginTeam>(LoginTeam.class);
    try {
      return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
