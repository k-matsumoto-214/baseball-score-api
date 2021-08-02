package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.Steal;
import basaball.score.form.StealForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.StealService;
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
public class StealController {
  @Autowired
  StealService stealService;
  @Autowired
  UtilService utilService;

  @PostMapping("/steals")
  public ResponseEntity<Object> createSteal(
      @AuthenticationPrincipal LoginTeam team, @RequestBody StealForm form) throws DataNotFoundException, RegistrationException {
    Steal steal = new Steal();
    steal.setEventId(form.getEventId());
    steal.setTeamId(team.getId());
    steal.setRunnerId(form.getRunnerId());
    steal.setPitcherId(form.getPitcherId());
    steal.setCatcherId(form.getCatcherId());
    steal.setSuccessFlg(form.getSuccessFlg());
    stealService.create(steal);
    return utilService.response();
  }

  @GetMapping("games/steals/{eventId}")
  public ResponseEntity<Object> fetchStealsbyEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(stealService.findByEventId(eventId, team.getId()));
  }
}
