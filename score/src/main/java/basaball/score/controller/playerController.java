package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.DeleteException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.entity.Player;
import basaball.score.form.PlayerForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.PlayerService;
import basaball.score.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class playerController {
  @Autowired
  private UtilService utilService;
  @Autowired
  private PlayerService playerService;

  @GetMapping("/players")
  public ResponseEntity<Object> fetchPlayers(@AuthenticationPrincipal LoginTeam team) throws DataNotFoundException {
    return utilService.responseFromObject(playerService.findByTeamId(team.getId()));
  }

  @PostMapping("/players")
  public ResponseEntity<Object> registerPlayer(@AuthenticationPrincipal LoginTeam team, @RequestBody PlayerForm form) throws RegistrationException {
    Player player = new Player();
    player.setTeamId(team.getId());
    player.setName(form.getName());
    player.setNumber(form.getNumber());
    player.setBirthday(form.getBirthday());
    player.setPosition(form.getPosition());
    player.setImage(form.getImage());
    player.setComment(form.getComment());
    playerService.create(player);
    return utilService.response();
  }

  @GetMapping("/players/{playerId}")
  public ResponseEntity<Object> fetchPlayer(@AuthenticationPrincipal LoginTeam team, @PathVariable int playerId) throws DataNotFoundException {
    return utilService.responseFromObject(playerService.findById(playerId, team.getId()));
  }

  @PostMapping("/players/{playerId}")
  public ResponseEntity<Object> updatePlayer(
      @AuthenticationPrincipal LoginTeam team, @PathVariable int playerId, @RequestBody PlayerForm form) throws DataNotFoundException, UpdateException {
    Player player = new Player();
    player.setId(playerId);
    player.setTeamId(team.getId());
    player.setName(form.getName());
    player.setNumber(form.getNumber());
    player.setImage(form.getImage());
    player.setBirthday(form.getBirthday());
    player.setPosition(form.getPosition());
    player.setComment(form.getComment());
    playerService.update(player);
    return utilService.response();
  }

  @DeleteMapping("/players/{playerId}")
  public ResponseEntity<Object> deletePlayer(@AuthenticationPrincipal LoginTeam team, @PathVariable int playerId) throws DeleteException {
    playerService.delete(playerId, team.getId());
    return utilService.response();
  }
}
