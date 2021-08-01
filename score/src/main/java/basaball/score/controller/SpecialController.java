package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.Special;
import basaball.score.form.SpecialForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.SpecialService;
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
public class SpecialController {
  @Autowired
  SpecialService specialService;
  @Autowired
  UtilService utilService;

  @PostMapping("/specials")
  public ResponseEntity<Object> createSpecial(
      @AuthenticationPrincipal LoginTeam team, @RequestBody SpecialForm form) throws DataNotFoundException, RegistrationException {
    Special special = new Special();
    special.setTeamId(team.getId());
    special.setEventId(form.getEventId());
    specialService.create(special);
    return utilService.response();
  }

  @GetMapping("/games/special/{eventId}")
  public ResponseEntity<Object> fetchSpecialByEventId(@AuthenticationPrincipal LoginTeam team, @PathVariable int eventId) throws DataNotFoundException {
    return utilService.responseFromObject(specialService.findByEventId(eventId, team.getId()));
  }
}
