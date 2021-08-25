package basaball.score.controller;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.entity.Game;
import basaball.score.form.GameForm;
import basaball.score.security.LoginTeam;
import basaball.score.service.GameService;
import basaball.score.service.UtilService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
  @Autowired
  private UtilService utilService;
  @Autowired
  private GameService gameService;

  @GetMapping("/games")
  public ResponseEntity<Object> fetchGames(@AuthenticationPrincipal LoginTeam team) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.findByTeamId(team.getId()));
  }

  @PostMapping("/games")
  public ResponseEntity<Object> createGame(
      @AuthenticationPrincipal LoginTeam team, @RequestBody GameForm form) throws DataNotFoundException, RegistrationException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    String topLineup = gson.toJson(form.getTopLineup());
    String bottomLineup = gson.toJson(form.getBottomLineup());
    Game game = new Game();
    game.setTeamId(team.getId());
    game.setOpponentTeam(form.getOpponentTeam());
    game.setDate(form.getDate());
    game.setField(form.getField());
    game.setTopFlg(form.isTopFlg());
    game.setTopLineup(topLineup);
    game.setBottomLineup(bottomLineup);
    return utilService.responseFromObject(gameService.create(game));
  }

  @GetMapping("/games/{gameId}")
  public ResponseEntity<Object> fetchGame(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.findById(gameId, team.getId()));
  }

  @PostMapping("games/{gameId}")
  public ResponseEntity<Object> updateGame(@AuthenticationPrincipal LoginTeam team, @RequestBody GameForm form, @PathVariable int gameId) throws UpdateException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    String topLineup = gson.toJson(form.getTopLineup());
    String bottomLineup = gson.toJson(form.getBottomLineup());
    Game game = new Game();
    game.setId(gameId);
    game.setTeamId(team.getId());
    game.setTopScore(form.getTopScore());
    game.setBottomScore(form.getBottomScore());
    game.setOpponentTeam(form.getOpponentTeam());
    game.setDate(form.getDate());
    game.setField(form.getField());
    game.setResult(form.getResult());
    game.setTopFlg(form.isTopFlg());
    game.setResultFlg(form.isResultFlg());
    game.setLineupingStatus(form.getLineupingStatus());
    game.setTopLineup(topLineup);
    game.setBottomLineup(bottomLineup);
    game.setInning(form.getInning());
    game.setWinningPitcher(form.getWinningPitcher());
    game.setLosingPitcher(form.getLosingPitcher());
    game.setSavePitcher(form.getSavePitcher());
    game.setComment(form.getComment());
    gameService.update(game);
    return utilService.response();
  }

  @GetMapping("/games/scores/{gameId}")
  public ResponseEntity<Object> fetchGameScores(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.getScores(gameId, team.getId()));
  }

  @GetMapping("/games/process/{gameId}")
  public ResponseEntity<Object> fetchGameProcess(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.getProcess(gameId, team.getId()));
  }

  @GetMapping("/games/stats/batters/{gameId}")
  public ResponseEntity<Object> fetchGameStatsForBatter(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.getStatsForBatter(gameId, team.getId()));
  }

  @GetMapping("/games/stats/pitchers/{gameId}")
  public ResponseEntity<Object> fetchGameStatsForPitcher(@AuthenticationPrincipal LoginTeam team, @PathVariable int gameId) throws DataNotFoundException {
    return utilService.responseFromObject(gameService.getStatsForPitcher(gameId, team.getId()));
  }
}
