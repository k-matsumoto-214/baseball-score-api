package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.BatteryError;
import basaball.score.form.BatteryErrorForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.BatteryErrorService;
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
public class BatteryErrorController {
  @Autowired
  private BatteryErrorService batteryErrorService;
  @Autowired
  private UtilService utilService;

  @PostMapping("/battery-errors")
  public ResponseEntity<Object> createBatteryError(
      @AuthenticationPrincipal LoginTeam team, @RequestBody BatteryErrorForm form) throws DataNotFoundException, RegistrationException {
    BatteryError batteryError = new BatteryError();
    batteryError.setTeamId(team.getId());
    batteryError.setEventId(form.getEventId());
    batteryError.setCatcherId(form.getCatcherId());
    batteryError.setPitcherId(form.getPitcherId());
    batteryError.setWpFlg(form.isWpFlg());
    batteryErrorService.create(batteryError);
    return utilService.response();
  }

  @GetMapping("/games/battery-errors/{eventId}")
  public ResponseEntity<Object> fetchBatteryErrorByEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(batteryErrorService.findByEventId(eventId, team.getId()));
  }
}
