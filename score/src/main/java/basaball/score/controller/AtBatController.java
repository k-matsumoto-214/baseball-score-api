package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.entity.AtBat;
import basaball.score.form.AtBatForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.AtBatService;
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
public class AtBatController {
  @Autowired
  private UtilService utilService;
  @Autowired
  private AtBatService atBatService;

  @GetMapping("/games/at-bats/{gameId}")
  public ResponseEntity<Object> fetchAtBatsForGame(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(atBatService.findByGameId(gameId));
  }

  @PostMapping("/at-bats")
  public ResponseEntity<Object> registerAtBat(@AuthenticationPrincipal LoginTeam team, @RequestBody AtBatForm form) throws RegistrationException {
    AtBat atBat = new AtBat();
    atBat.setTeamId(team.getId());
    atBat.setGameId(form.getGameId());
    atBat.setBatterId(form.getBatterId());
    atBat.setPitcherId(form.getPitcherId());
    atBat.setInning(form.getInning());
    atBat.setOutCount(form.getOutCount());
    atBat.setFirstRunnerId(form.getFirstRunnerId());
    atBat.setSecondRunnerId(form.getSecondRunnerId());
    atBat.setThirdRunnerId(form.getThirdRunnerId());
    atBat.setPlayerChangeFlg(form.isPlayerChangeFlg());
    atBat.setDirection(form.getDirection());
    atBat.setCompleteFlg(form.getCompleteFlg());
    atBat.setComment(form.getComment());
    atBat.setResult(form.getResult());
    atBat.setLineupNumber(form.getLineupNumber());
    atBat.setTopFlg(form.isTopFlg());
    atBatService.create(atBat);
    return utilService.response();
  }

  @PostMapping("/at-bats/{atBatId}")
  public ResponseEntity<Object> registerAtBat(@AuthenticationPrincipal LoginTeam team, @RequestBody AtBatForm form, @PathVariable int atBatId) throws RegistrationException, UpdateException {
    AtBat atBat = new AtBat();
    atBat.setId(atBatId);
    atBat.setTeamId(team.getId());
    atBat.setGameId(form.getGameId());
    atBat.setBatterId(form.getBatterId());
    atBat.setPitcherId(form.getPitcherId());
    atBat.setInning(form.getInning());
    atBat.setOutCount(form.getOutCount());
    atBat.setFirstRunnerId(form.getFirstRunnerId());
    atBat.setSecondRunnerId(form.getSecondRunnerId());
    atBat.setThirdRunnerId(form.getThirdRunnerId());
    atBat.setPlayerChangeFlg(form.isPlayerChangeFlg());
    atBat.setDirection(form.getDirection());
    atBat.setCompleteFlg(form.getCompleteFlg());
    atBat.setComment(form.getComment());
    atBat.setResult(form.getResult());
    atBat.setLineupNumber(form.getLineupNumber());
    atBat.setTopFlg(form.isTopFlg());
    atBatService.update(atBat);
    return utilService.response();
  }

  @PostMapping("games/at-bats/{atBatId}")
  public ResponseEntity<Object> deleteAtBat(@AuthenticationPrincipal LoginTeam team, @RequestBody AtBatForm form, @PathVariable int atBatId) throws RegistrationException, UpdateException {
    AtBat atBat = new AtBat();
    atBat.setId(atBatId);
    atBat.setTeamId(team.getId());

    AtBat beforeAtBat = new AtBat();
    beforeAtBat.setId(form.getId());
    beforeAtBat.setTeamId(team.getId());
    beforeAtBat.setGameId(form.getGameId());
    beforeAtBat.setBatterId(form.getBatterId());
    beforeAtBat.setPitcherId(form.getPitcherId());
    beforeAtBat.setInning(form.getInning());
    beforeAtBat.setOutCount(form.getOutCount());
    beforeAtBat.setFirstRunnerId(form.getFirstRunnerId());
    beforeAtBat.setSecondRunnerId(form.getSecondRunnerId());
    beforeAtBat.setThirdRunnerId(form.getThirdRunnerId());
    beforeAtBat.setPlayerChangeFlg(form.isPlayerChangeFlg());
    beforeAtBat.setDirection(form.getDirection());
    beforeAtBat.setCompleteFlg(form.getCompleteFlg());
    beforeAtBat.setComment(form.getComment());
    beforeAtBat.setResult(form.getResult());
    beforeAtBat.setLineupNumber(form.getLineupNumber());
    beforeAtBat.setTopFlg(form.isTopFlg());

    atBatService.deleteAtBat(beforeAtBat, atBat);
    return utilService.response();
  }
}
