package basaball.score.controller;

import static basaball.score.security.SecurityConstants.SIGNUP_URL;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.DeleteException;
import basaball.score.controller.exception.DuplicateAccountIdException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.entity.Team;
import basaball.score.form.TeamForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.TeamsService;
import basaball.score.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
  @Autowired
  private UtilService utilService;
  @Autowired
  private TeamsService teamsService;

  @GetMapping("/teams")
  public ResponseEntity<Object> fetchPlayers(@AuthenticationPrincipal LoginTeam team) throws DataNotFoundException {
    return utilService.responseFromObject(teamsService.findById(team.getId()));
  }

  @PostMapping(SIGNUP_URL)
  public ResponseEntity<Object> registerPlayer(@RequestBody TeamForm form) throws RegistrationException, DuplicateAccountIdException {
    Team team = new Team();
    team.setAccountId(form.getAccountId());
    team.setPassword(form.getPassword());
    team.setComment(form.getComment());
    team.setImage(form.getImage());
    team.setName(form.getName());
    teamsService.create(team);
    return utilService.response();
  }

  @PostMapping("/teams")
  public ResponseEntity<Object> updateTeam(
      @AuthenticationPrincipal LoginTeam team, @RequestBody TeamForm form) throws DataNotFoundException, UpdateException {
    Team updTeam = new Team();
    updTeam.setId(team.getId());
    updTeam.setName(form.getName());
    updTeam.setComment(form.getComment());
    updTeam.setImage(form.getImage());
    teamsService.update(updTeam);
    return utilService.response();
  }

  @DeleteMapping("/teams")
  public ResponseEntity<Object> deletePlayer(@AuthenticationPrincipal LoginTeam team) throws DeleteException {
    teamsService.delete(team.getId());
    return utilService.response();
  }
}
