package basaball.score.security;

import static basaball.score.security.SecurityConstants.EXPIRATION_TIME;
import static basaball.score.security.SecurityConstants.LOGIN_URL;
import static basaball.score.security.SecurityConstants.SECRET;
import static basaball.score.security.SecurityConstants.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  public LoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, "POST"));
    setUsernameParameter("accountId");
    setPasswordParameter("password");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      // requestパラメータからユーザ情報を読み取る
      LoginForm form = new ObjectMapper().readValue(req.getInputStream(), LoginForm.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(form.getAccountId(), form.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // 認証に成功した場合の処理
  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {
    String token = Jwts.builder()
                       .setSubject(Integer.toString(((LoginTeam) auth.getPrincipal()).getId()))
                       .claim("name", ((LoginTeam) auth.getPrincipal()).getName())
                       .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                       .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                       .compact();

    JSONObject json = new JSONObject();
    try {
      json.put("token", TOKEN_PREFIX + token);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    res.setContentType("application/json; charset=utf8");
    res.setCharacterEncoding("utf8");
    PrintWriter out = res.getWriter();
    out.print(json.toString());
  }
}
