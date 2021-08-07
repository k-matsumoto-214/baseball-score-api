package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.PlayerChange;
import basaball.score.form.PlayerChangeForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.PlayerChangeService;
import basaball.score.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerChangeController {
  @Autowired
  private PlayerChangeService playerChangeService;
  @Autowired
  private UtilService utilService;

  @PostMapping("/player-changes")
  public ResponseEntity<Object> createPlayerChanges(
      @AuthenticationPrincipal LoginTeam team, @RequestBody PlayerChangeForm form) throws DataNotFoundException, RegistrationException {
    PlayerChange playerChange = new PlayerChange();
    playerChange.setTeamId(team.getId());
    playerChange.setGameId(form.getGameId());
    playerChange.setAtBatId(form.getAtBatId());
    playerChange.setInPlayerId(form.getInPlayerId());
    playerChange.setOutPlayerId(form.getOutPlayerId());
    playerChange.setChangeStatus(form.getChangeStatus());
    playerChange.setBeforeField(form.getBeforeField());
    playerChange.setAfterField(form.getAfterField());
    playerChange.setEventId(form.getEventId());
    playerChangeService.create(playerChange);
    return utilService.response();
  }

  @GetMapping("games/player-changes/{eventId}")
  public ResponseEntity<Object> fetchRunsbyEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(playerChangeService.findByEventId(eventId, team.getId()));
  }
}
