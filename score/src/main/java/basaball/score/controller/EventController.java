package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.entity.Event;
import basaball.score.form.EventForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.EventService;
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
public class EventController {
  @Autowired
  private UtilService utilService;
  @Autowired
  private EventService eventService;

  @PostMapping("/events")
  public ResponseEntity<Object> createEvent(
      @AuthenticationPrincipal LoginTeam team, @RequestBody EventForm form) throws DataNotFoundException, RegistrationException {
    Event event = new Event();
    event.setGameId(form.getGameId());
    event.setTeamId(team.getId());
    event.setInning(form.getInning());
    event.setAtBatId(form.getAtBatId());
    event.setResultFirstRunnerId(form.getResultFirstRunnerId());
    event.setResultSecondRunnerId(form.getResultSecondRunnerId());
    event.setResultThirdRunnerId(form.getResultThirdRunnerId());
    event.setResultOutCount(form.getResultOutCount());
    event.setTiming(form.getTiming());
    event.setEventType(form.getEventType());
    event.setComment(form.getComment());
    return utilService.responseFromObject(eventService.create(event));
  }

  @GetMapping("games/events/{atBatId}")
  public ResponseEntity<Object> fetchEventsForGame(@AuthenticationPrincipal LoginTeam team, @PathVariable int atBatId) throws DataNotFoundException {
    return utilService.responseFromObject(eventService.findByAtBatId(atBatId, team.getId()));
  }
}
