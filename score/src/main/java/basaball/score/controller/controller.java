package basaball.score.controller;

import basaball.score.security.LoginTeam;
import basaball.score.service.PlayerService;
import basaball.score.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
  @Autowired
  private UtilService utilService;
  @Autowired
  private PlayerService playerService;

  @GetMapping("/players/{playerId}")
  public ResponseEntity<Object> fetchPlayer(@AuthenticationPrincipal LoginTeam team, @PathVariable int playerId) {
    return utilService.responseFromObject(inputObject);
  }

  @GetMapping("/players")
  public ResponseEntity<Object> fetchPlayer(@AuthenticationPrincipal LoginTeam team, @PathVariable int playerId) {
    return utilService.response();
  }
}
