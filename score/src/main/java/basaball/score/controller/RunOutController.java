package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.RunOut;
import basaball.score.form.RunOutForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.RunOutService;
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
public class RunOutController {
  @Autowired
  RunOutService runOutService;
  @Autowired
  UtilService utilService;

  @PostMapping("/run-outs")
  public ResponseEntity<Object> createRunOut(
      @AuthenticationPrincipal LoginTeam team, @RequestBody RunOutForm form) throws DataNotFoundException, RegistrationException {
    RunOut runOut = new RunOut();
    runOut.setPlayerId(form.getPlayerId());
    runOut.setTeamId(team.getId());
    runOut.setEventId(form.getEventId());
    runOutService.create(runOut);
    return utilService.response();
  }

  @GetMapping("games/run-outs/{eventId}")
  public ResponseEntity<Object> fetchRunsbyEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(runOutService.findByEventId(eventId, team.getId()));
  }
}
