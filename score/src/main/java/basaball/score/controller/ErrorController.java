package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.Error;
import basaball.score.form.ErrorForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.ErrorService;
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
public class ErrorController {
  @Autowired
  private ErrorService errorService;
  @Autowired
  private UtilService utilService;

  @PostMapping("/errors")
  public ResponseEntity<Object> createError(
      @AuthenticationPrincipal LoginTeam team, @RequestBody ErrorForm form) throws DataNotFoundException, RegistrationException {
    Error error = new Error();
    error.setTeamId(team.getId());
    error.setEventId(form.getEventId());
    error.setPlayerId(form.getPlayerId());
    errorService.create(error);
    return utilService.response();
  }

  @GetMapping("/games/errors/{eventId}")
  public ResponseEntity<Object> fetchErrorByEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(errorService.findByEventId(eventId, team.getId()));
  }
}
