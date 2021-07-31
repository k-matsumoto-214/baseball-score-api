package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.Run;
import basaball.score.form.RunForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.RunService;
import basaball.score.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {
  @Autowired
  RunService runService;
  @Autowired
  UtilService utilService;

  @PostMapping("/runs")
  public ResponseEntity<Object> createRun(
      @AuthenticationPrincipal LoginTeam team, @RequestBody RunForm form) throws DataNotFoundException, RegistrationException {
    Run run = new Run();
    run.setTeamId(team.getId());
    run.setGameId(form.getGameId());
    run.setEventId(form.getEventId());
    run.setAtBatId(form.getAtBatId());
    run.setBatterId(form.getBatterId());
    run.setPitcherId(form.getPitcherId());
    run.setRunnerId(form.getRunnerId());
    run.setInning(form.getInning());
    run.setEarndFlg(form.isEarndFlg());
    run.setRbiFlg(form.isRbiFlg());
    runService.create(run);
    return utilService.response();
  }
}
