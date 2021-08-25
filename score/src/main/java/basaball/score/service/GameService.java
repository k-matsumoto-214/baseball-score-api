package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.controller.exception.UpdateException;
import basaball.score.dao.AtBatsDao;
import basaball.score.dao.BatteryErrorsDao;
import basaball.score.dao.ErrorsDao;
import basaball.score.dao.EventsDao;
import basaball.score.dao.GamesDao;
import basaball.score.dao.PlayerChangeDao;
import basaball.score.dao.PlayersDao;
import basaball.score.dao.RunOutsDao;
import basaball.score.dao.RunsDao;
import basaball.score.dao.StealsDao;
import basaball.score.dao.TeamsDao;
import basaball.score.entity.AtBat;
import basaball.score.entity.BatteryError;
import basaball.score.entity.Error;
import basaball.score.entity.Event;
import basaball.score.entity.Game;
import basaball.score.entity.Player;
import basaball.score.entity.PlayerChange;
import basaball.score.entity.Run;
import basaball.score.entity.RunOut;
import basaball.score.entity.Steal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
  @Autowired
  private GamesDao gamesDao;
  @Autowired
  private AtBatsDao atBatsDao;
  @Autowired
  private RunService runService;
  @Autowired
  private EventsDao eventsDao;
  @Autowired
  private StealsDao stealsDao;
  @Autowired
  private RunsDao runsDao;
  @Autowired
  private RunOutsDao runOutsDao;
  @Autowired
  private BatteryErrorsDao batteryErrorsDao;
  @Autowired
  private ErrorsDao errorsDao;
  @Autowired
  private PlayersDao playersDao;
  @Autowired
  private TeamsDao teamsDao;
  @Autowired
  private PlayerChangeDao playerChangeDao;

  public Map<String, Object> create(Game game) throws RegistrationException {
    if (gamesDao.create(game) != 1) {
      throw new RegistrationException("試合登録に失敗しました。");
    }
    int id = gamesDao.fetchLastInsertId();
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", id);
    return result;
  }

  public List<Map<String, Object>> findByTeamId(int teamId) throws DataNotFoundException {
    List<Game> games = gamesDao.findByTeamId(teamId);
    if (games == null) {
      throw new DataNotFoundException("試合情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (Game game : games) {
      JsonArray topLineup = JsonParser.parseString(game.getTopLineup()).getAsJsonArray();
      JsonArray bottomLineup = JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", game.getId());
      tempMap.put("teamId", game.getTeamId());
      tempMap.put("opponentTeam", game.getOpponentTeam());
      tempMap.put("topScore", game.getTopScore());
      tempMap.put("bottomScore", game.getBottomScore());
      tempMap.put("date", game.getDate() != null ? sdf.format(game.getDate()) : null);
      tempMap.put("field", game.getField());
      tempMap.put("result", game.getResult());
      tempMap.put("topFlg", game.isTopFlg());
      tempMap.put("resultFlg", game.isResultFlg());
      tempMap.put("lineupingStatus", game.getLineupingStatus());
      tempMap.put("topLineup", topLineup);
      tempMap.put("bottomLineup", bottomLineup);
      result.add(tempMap);
    }

    return result;
  }

  public Map<String, Object> findById(int gameId, int teamId) throws DataNotFoundException {
    Game game = gamesDao.findById(gameId, teamId);
    if (game == null) {
      throw new DataNotFoundException("試合情報が見つかりません。");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    JsonArray topLineup = JsonParser.parseString(game.getTopLineup()).getAsJsonArray();
    JsonArray bottomLineup = JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", game.getId());
    result.put("teamId", game.getTeamId());
    result.put("opponentTeam", game.getOpponentTeam());
    result.put("topScore", game.getTopScore());
    result.put("bottomScore", game.getBottomScore());
    result.put("date", game.getDate() != null ? sdf.format(game.getDate()) : null);
    result.put("field", game.getField());
    result.put("result", game.getResult());
    result.put("topFlg", game.isTopFlg());
    result.put("resultFlg", game.isResultFlg());
    result.put("lineupingStatus", game.getLineupingStatus());
    result.put("topLineup", topLineup);
    result.put("bottomLineup", bottomLineup);
    result.put("inning", game.getInning());
    result.put("winningPitcher", game.getWinningPitcher());
    result.put("losingPitcher", game.getLosingPitcher());
    result.put("savePitcher", game.getSavePitcher());
    result.put("comment", game.getComment());
    return result;
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(Game game) throws UpdateException {
    gamesDao.selectForUpdate(game.getId());
    if (gamesDao.update(game) != 1) {
      throw new UpdateException("試合情報更新に失敗しました。");
    }
  }

  public Map<String, Object> getScores(int gameId, int teamId) throws DataNotFoundException {
    Map<String, Object> result = new LinkedHashMap<>();
    Map<String, Object> runs = runService.findByGameId(gameId, teamId);
    List<AtBat> atBats = atBatsDao.findByGameId(gameId);

    int topError = 0;
    int bottomError = 0;
    int topHit = 0;
    int bottomHit = 0;

    for (AtBat atBat : atBats) {
      if ((atBat.isTopFlg() && atBat.getResult() != null) && (atBat.getResult() == 0 || atBat.getResult() == 1 || atBat.getResult() == 2 || atBat.getResult() == 3)) {
        topHit++;
      }
      if ((atBat.isTopFlg() && atBat.getResult() != null) && (atBat.getResult() == 6 || atBat.getResult() == 10)) {
        bottomError++;
      }
      if ((!atBat.isTopFlg() && atBat.getResult() != null) && (atBat.getResult() == 0 || atBat.getResult() == 1 || atBat.getResult() == 2 || atBat.getResult() == 3)) {
        bottomHit++;
      }
      if ((!atBat.isTopFlg() && atBat.getResult() != null) && (atBat.getResult() == 6 || atBat.getResult() == 10)) {
        topError++;
      }
    }

    List<Event> events = eventsDao.findByGameId(gameId, teamId);
    if (events != null) {
      for (Event event : events) {
        if (event.getEventType() != null && (event.getEventType() == 1 || event.getEventType() == 2)) {
          AtBat atBat = atBatsDao.findById(event.getAtBatId());
          if (atBat.isTopFlg()) {
            topError++;
          } else {
            bottomError++;
          }
        }
      }
    }

    result.put("runs", runs);
    result.put("topHit", topHit);
    result.put("topError", topError);
    result.put("bottomHit", bottomHit);
    result.put("bottomError", bottomError);

    return result;
  }

  public List<Map<String, Object>> getProcess(int gameId, int teamId) {
    List<Map<String, Object>> result = new ArrayList<>();
    List<AtBat> atBats = atBatsDao.findByGameId(gameId);

    Game game = gamesDao.findById(gameId, teamId);
    String topTeam = game.isTopFlg() ? teamsDao.findById(teamId).getName() : game.getOpponentTeam();
    String bottomTeam = game.isTopFlg() ? game.getOpponentTeam() : teamsDao.findById(teamId).getName();
    int inning = 1;
    boolean topFlg = true;
    int topScore = 0;
    int bottomScore = 0;
    String situation = "無死 走者なし";

    Map<String, Object> inningProcess = new LinkedHashMap<>();
    List<Map<String, Object>> batterProcesses = new ArrayList<>();

    for (AtBat atBat : atBats) {
      if ((inning != atBat.getInning()) || (topFlg != atBat.isTopFlg())) {
        inningProcess.put("inningInfo", (inning + "回" + (topFlg ? "表　" : "裏　") + (topFlg ? topTeam : bottomTeam) + "の攻撃"));
        inningProcess.put("batterProcesses", batterProcesses);
        result.add(inningProcess);
        inningProcess = new LinkedHashMap<>(); // イニングの変わり目でイニング経過を初期化
        batterProcesses = new ArrayList<>();
        inning = atBat.getInning();
        topFlg = atBat.isTopFlg();
      }

      Map<String, Object> batterProcess = new LinkedHashMap<>();
      List<String> beforeBattingEvents = new ArrayList<>();
      List<String> afterBattingEvents = new ArrayList<>();

      Player batter = playersDao.findByIdOnly(atBat.getBatterId());
      batterProcess.put("batter", atBat.getLineupNumber() + "番 " + batter.getName());
      batterProcess.put("situation", situation);

      List<Event> events = eventsDao.findByAtBatId(atBat.getId(), teamId);
      if (events != null) {
        for (Event event : events) {
          if (event.getComment() != null) {
            if (event.getTiming() == 0) {
              beforeBattingEvents.add(event.getComment());
            } else if (event.getTiming() == 1) {
              afterBattingEvents.add(event.getComment());
            }
          }
          if (event.getEventType() != null && event.getEventType() == 0) {
            List<Steal> steals = stealsDao.findByEventId(event.getId(), teamId);
            for (Steal steal : steals) {
              Player player = playersDao.findByIdOnly(steal.getRunnerId());
              beforeBattingEvents.add(player.getName() + " 盗塁" + (steal.isSuccessFlg() ? "成功" : "失敗"));
            }
          }
          if (event.getEventType() != null && event.getEventType() == 1) {
            BatteryError batteryError = batteryErrorsDao.findByEventId(event.getId(), teamId);
            Player player = playersDao.findByIdOnly((batteryError.isWpFlg() ? batteryError.getPitcherId() : batteryError.getCatcherId()));
            beforeBattingEvents.add(player.getName() + " " + (batteryError.isWpFlg() ? "暴投" : "パスボール"));
          }
          if (event.getEventType() != null && event.getEventType() == 2) {
            Error error = errorsDao.findByEventId(event.getId(), teamId);
            Player player = playersDao.findByIdOnly(error.getPlayerId());
            beforeBattingEvents.add(player.getName() + " エラー");
          }
          if (event.getEventType() != null && event.getEventType() == 3) {
            beforeBattingEvents.add("特殊プレー");
          }
          if (event.getEventType() != null && event.getEventType() == 4) {
            List<PlayerChange> playerChanges = playerChangeDao.findByEventId(event.getId(), teamId);
            for (PlayerChange playerChange : playerChanges) {
              Player inPlayer = playersDao.findByIdOnly(playerChange.getInPlayerId());
              Player outPlayer = playersDao.findByIdOnly(playerChange.getOutPlayerId());
              if (inPlayer.getId() == outPlayer.getId()) {
                if (playerChange.getChangeStatus() == 0) {
                  beforeBattingEvents.add("投手交代 " + outPlayer.getName() + " " + this.formatField(playerChange.getBeforeField()) +
                                          " ⇒ " + this.formatField(playerChange.getAfterField()));
                } else if (playerChange.getChangeStatus() == 1) {
                  beforeBattingEvents.add("守備交代 " + outPlayer.getName() + " " + this.formatField(playerChange.getBeforeField()) +
                                          " ⇒ " + this.formatField(playerChange.getAfterField()));
                }
              } else {
                if (playerChange.getChangeStatus() == 0) {
                  beforeBattingEvents.add("投手交代 " + outPlayer.getName() + " ⇒ " + inPlayer.getName());
                } else if (playerChange.getChangeStatus() == 1) {
                  beforeBattingEvents.add("守備交代 " + outPlayer.getName() + " " + this.formatField(playerChange.getBeforeField()) +
                                          " ⇒ " + inPlayer.getName() + " " + this.formatField(playerChange.getAfterField()));
                } else if (playerChange.getChangeStatus() == 2) {
                  beforeBattingEvents.add("代打 " + outPlayer.getName() + " ⇒ " + inPlayer.getName());
                } else if (playerChange.getChangeStatus() == 3) {
                  beforeBattingEvents.add("代走 " + outPlayer.getName() + " ⇒ " + inPlayer.getName());
                }
              }
            }
          }
          if (event.getEventType() == null && event.getTiming() == 1) {
            if (atBat.getResult() == 7 && runsDao.findByEventId(event.getId(), teamId) != null) {
              batterProcess.put("battingResult", this.formatBattingResult(atBat).replace("送りバント", "スクイズ"));
            } else {
              batterProcess.put("battingResult", this.formatBattingResult(atBat));
            }
            if (atBat.getComment() != null) {
              batterProcess.put("battingResultComment", atBat.getComment());
            }
          }
          List<Run> runs = runsDao.findByEventId(event.getId(), teamId);
          if (runs != null) {
            for (Run run : runs) {
              if (run.isTopFlg()) {
                topScore++;
              } else {
                bottomScore++;
              }
              Player player = playersDao.findByIdOnly(run.getRunnerId());
              if (event.getTiming() == 0) {
                beforeBattingEvents.add(player.getName() + " ホームイン " + topTeam + " " + topScore + " 対 " + bottomScore + " " + bottomTeam);
              } else if (event.getTiming() == 1) {
                afterBattingEvents.add(player.getName() + " ホームイン " + topTeam + " " + topScore + " 対 " + bottomScore + " " + bottomTeam);
              }
            }
          }
          List<RunOut> runOuts = runOutsDao.findByEventId(event.getId(), teamId);
          if (runOuts != null) {
            for (RunOut runOut : runOuts) {
              if (event.getTiming() == 0) {
                Player player = playersDao.findByIdOnly(runOut.getPlayerId());
                beforeBattingEvents.add(player.getName() + " 走塁死");
              } else if (event.getTiming() == 1) {
                if (batter.getId() != runOut.getPlayerId()) {
                  Player player = playersDao.findByIdOnly(runOut.getPlayerId());
                  afterBattingEvents.add(player.getName() + " 走塁死");
                }
              }
            }
          }
          if (event.getResultOutCount() != 3) {
            if (event.getTiming() == 0) {
              beforeBattingEvents.add(this.formatOutCount(event) + " " + this.formatRunner(event));
            } else if (event.getTiming() == 1) {
              afterBattingEvents.add(this.formatOutCount(event) + " " + this.formatRunner(event));
              situation = this.formatOutCount(event) + " " + this.formatRunner(event);
            }
          } else {
            if (atBat.getId() != atBats.get(atBats.size() - 2).getId()) {
              if (event.getTiming() == 0) {
                beforeBattingEvents.add("スリーアウト チェンジ");
              } else if (event.getTiming() == 1) {
                afterBattingEvents.add("スリーアウト チェンジ");
              }
            } else if (!game.isResultFlg()) {
              if (event.getTiming() == 0) {
                beforeBattingEvents.add("スリーアウト チェンジ");
              } else if (event.getTiming() == 1) {
                afterBattingEvents.add("スリーアウト チェンジ");
              }
            }
            situation = "無死 走者なし";
          }
        }
        if (atBat.getId() == atBats.get(atBats.size() - 2).getId() && game.isResultFlg()) {
          // 試合が終了していれば最終行に挿入
          afterBattingEvents.add("試合終了");
        }

        batterProcess.put("afterBattingEvents", afterBattingEvents);
        batterProcess.put("beforeBattingEvents", beforeBattingEvents);
      }
      batterProcesses.add(batterProcess);

      if (atBat.getId() == atBats.get(atBats.size() - 1).getId() && !game.isResultFlg()) {
        inningProcess.put("inningInfo", (inning + "回" + (topFlg ? "表　" : "裏　") + (topFlg ? topTeam : bottomTeam) + "の攻撃"));
        batterProcesses.remove(batterProcesses.size() - 1);
        inningProcess.put("batterProcesses", batterProcesses);
        result.add(inningProcess);
      }
    }
    return result;
  }

  private String formatRunner(Event event) {
    if (event.getResultFirstRunnerId() != null && event.getResultSecondRunnerId() != null && event.getResultThirdRunnerId() != null) {
      return " 満塁";
    }
    if (event.getResultFirstRunnerId() != null && event.getResultSecondRunnerId() != null && event.getResultThirdRunnerId() == null) {
      return " 1, 2塁";
    }
    if (event.getResultFirstRunnerId() != null && event.getResultSecondRunnerId() == null && event.getResultThirdRunnerId() != null) {
      return " 1, 3塁";
    }
    if (event.getResultFirstRunnerId() == null && event.getResultSecondRunnerId() != null && event.getResultThirdRunnerId() != null) {
      return " 2, 3塁";
    }
    if (event.getResultFirstRunnerId() != null && event.getResultSecondRunnerId() == null && event.getResultThirdRunnerId() == null) {
      return " 1塁";
    }
    if (event.getResultFirstRunnerId() == null && event.getResultSecondRunnerId() != null && event.getResultThirdRunnerId() == null) {
      return " 2塁";
    }
    if (event.getResultFirstRunnerId() == null && event.getResultSecondRunnerId() == null && event.getResultThirdRunnerId() != null) {
      return " 3塁";
    }
    return " 走者なし";
  }

  private String formatBattingResult(AtBat atBat) {
    String direction = "";
    if (atBat.getDirection() != null) {
      if (atBat.getDirection() == 1) {
        direction = "ピッチャー";
      } else if (atBat.getDirection() == 2) {
        direction = "キャッチャー";
      } else if (atBat.getDirection() == 3) {
        direction = "ファースト";
      } else if (atBat.getDirection() == 4) {
        direction = "セカンド";
      } else if (atBat.getDirection() == 5) {
        direction = "サード";
      } else if (atBat.getDirection() == 6) {
        direction = "ショート";
      } else if (atBat.getDirection() == 7) {
        direction = "レフト";
      } else if (atBat.getDirection() == 8) {
        direction = "センター";
      } else if (atBat.getDirection() == 9) {
        direction = "ライト";
      }
    } else {
      direction = "";
    }

    if (atBat.getResult() == 0) {
      return direction + "へのヒット";
    }
    if (atBat.getResult() == 1) {
      return direction + "へのツーべース";
    }
    if (atBat.getResult() == 2) {
      return direction + "へのスリーベース";
    }
    if (atBat.getResult() == 3) {
      return direction + "へのホームラン";
    }
    if (atBat.getResult() == 4) {
      return "フォアボール";
    }
    if (atBat.getResult() == 5) {
      return "デッドボール";
    }
    if (atBat.getResult() == 6) {
      return direction + "のエラー";
    }
    if (atBat.getResult() == 7) {
      return direction + "への送りバント";
    }
    if (atBat.getResult() == 8) {
      return direction + "への犠牲フライ";
    }
    if (atBat.getResult() == 9) {
      return "三振";
    }
    if (atBat.getResult() == 10) {
      return "振り逃げ";
    }
    if (atBat.getResult() == 11) {
      return direction + "へのゴロ";
    }
    if (atBat.getResult() == 12) {
      return direction + "へのフライ";
    }
    if (atBat.getResult() == 13) {
      return "特殊プレーで出塁";
    }
    if (atBat.getResult() == 14) {
      return "特殊なプレーでアウト";
    }

    return "";
  }

  private String formatOutCount(Event event) {
    if (event.getResultOutCount() == 0) {
      return "無死";
    }
    if (event.getResultOutCount() == 1) {
      return "一死";
    }
    if (event.getResultOutCount() == 2) {
      return "二死";
    }
    return "";
  }

  public List<Map<String, Object>> getStatsForBatter(int gameId, int teamId) {
    Game game = gamesDao.findById(gameId, teamId);
    JsonArray lineups = game.isTopFlg()
                            ? JsonParser.parseString(game.getTopLineup()).getAsJsonArray()
                            : JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();

    List<Map<String, Object>> result = new ArrayList<>();
    List<Integer> registerdPlayers = new ArrayList<>();
    for (JsonElement lineup : lineups) {
      JsonArray orderDetails = lineup.getAsJsonObject().get("orderDetails").getAsJsonArray();
      for (JsonElement playerInfo : orderDetails) {
        int playerId = playerInfo.getAsJsonObject().get("playerId").getAsInt();
        if (!registerdPlayers.contains(playerId)) {
          registerdPlayers.add(playerId);
          Map<String, Object> tempMap = new LinkedHashMap<>();
          Player player = playersDao.findByIdOnly(playerId);
          List<AtBat> playerAtBats = new ArrayList<>();
          List<AtBat> atBats = atBatsDao.findByGameId(gameId);
          if (atBats != null) {
            for (AtBat atBat : atBats) {
              if (atBat.getBatterId() == playerId) {
                playerAtBats.add(atBat);
              }
            }
          }

          int appear = 0;
          int hit = 0;
          int two = 0;
          int three = 0;
          int hr = 0;
          int k = 0;
          int bb = 0;
          int steal = 0;
          int sac = 0;
          int rbi = 0;
          int score = 0;

          if (playerAtBats != null) {
            for (AtBat atBat : playerAtBats) {
              if (atBat.getCompleteFlg() != null && atBat.getCompleteFlg()) {
                appear++;
                switch (atBat.getResult()) {
                  case 0:
                    hit++;
                    break;
                  case 1:
                    two++;
                    break;
                  case 2:
                    three++;
                    break;
                  case 3:
                    hr++;
                    break;
                  case 4:
                    bb++;
                    break;
                  case 5:
                    bb++;
                    break;
                  case 7:
                    sac++;
                    break;
                  case 8:
                    sac++;
                    break;
                  case 9:
                    k++;
                    break;
                  case 10:
                    k++;
                    break;
                }
              }
            }
          }
          List<Run> rbis = runsDao.findByBatterIdAndGameId(playerId, gameId);
          if (rbis != null) {
            for (Run run : rbis) {
              if (run.isRbiFlg()) {
                rbi++;
              }
            }
          }
          List<Run> scores = runsDao.findByRunnerIdAndGameId(playerId, gameId);
          if (scores != null) {
            score = scores.size();
          }
          List<Event> events = eventsDao.findByGameId(gameId, teamId);
          if (events != null) {
            for (Event event : events) {
              if (event.getEventType() != null && event.getEventType() == 0) {
                List<Steal> steals = stealsDao.findByRunnerIdAndEventId(playerId, event.getId());
                if (steals != null) {
                  for (Steal tempSteal : steals) {
                    if (tempSteal.isSuccessFlg() && tempSteal.getRunnerId() == playerId) {
                      steal++;
                    }
                  }
                }
              }
            }
          }
          tempMap.put("name", player.getName());
          tempMap.put("rbi", rbi);
          tempMap.put("k", k);
          tempMap.put("hit", hit);
          tempMap.put("two", two);
          tempMap.put("three", three);
          tempMap.put("hr", hr);
          tempMap.put("bb", bb);
          tempMap.put("sac", sac);
          tempMap.put("steal", steal);
          tempMap.put("score", score);
          tempMap.put("appear", appear);
          tempMap.put("batting", appear - bb - sac);
          result.add(tempMap);
        }
      }
    }
    return result;
  }

  public List<Map<String, Object>> getStatsForPitcher(int gameId, int teamId) {
    Game game = gamesDao.findById(gameId, teamId);
    JsonArray lineups = game.isTopFlg()
                            ? JsonParser.parseString(game.getTopLineup()).getAsJsonArray()
                            : JsonParser.parseString(game.getBottomLineup()).getAsJsonArray();

    List<Map<String, Object>> result = new ArrayList<>();
    List<Integer> registerdPlayers = new ArrayList<>();
    for (JsonElement lineup : lineups) {
      JsonArray orderDetails = lineup.getAsJsonObject().get("orderDetails").getAsJsonArray();
      for (JsonElement playerInfo : orderDetails) {
        int playerId = playerInfo.getAsJsonObject().get("playerId").getAsInt();
        if (!registerdPlayers.contains(playerId) && playerInfo.getAsJsonObject().get("fieldNumber").getAsInt() == 1) {
          registerdPlayers.add(playerId);
          Map<String, Object> tempMap = new LinkedHashMap<>();
          Player player = playersDao.findByIdOnly(playerId);
          List<AtBat> playerAtBats = new ArrayList<>();
          List<AtBat> atBats = atBatsDao.findByGameId(gameId);
          if (atBats != null) {
            for (AtBat atBat : atBats) {
              if (atBat.getPitcherId() == playerId) {
                playerAtBats.add(atBat);
              }
            }
          }

          int gotOuts = 0;
          int hit = 0;
          int hr = 0;
          int k = 0;
          int bb = 0;
          int er = 0;
          int r = 0;

          if (playerAtBats != null) {
            int outCount = 0;
            for (AtBat atBat : playerAtBats) {
              if (atBat.getCompleteFlg() != null && atBat.getCompleteFlg()) {
                switch (atBat.getResult()) {
                  case 0:
                    hit++;
                    break;
                  case 1:
                    hit++;
                    break;
                  case 2:
                    hit++;
                    break;
                  case 3:
                    hit++;
                    hr++;
                    break;
                  case 4:
                    bb++;
                    break;
                  case 5:
                    bb++;
                    break;
                  case 9:
                    k++;
                    break;
                  case 10:
                    k++;
                    break;
                }
              }
              List<Event> events = eventsDao.findByAtBatId(atBat.getId(), teamId);
              if (events != null) {
                for (Event event : events) {
                  if (outCount != event.getResultOutCount()) {
                    gotOuts += event.getResultOutCount() - outCount;
                    outCount += (event.getResultOutCount() - outCount);
                    if (outCount == 3) {
                      outCount = 0;
                    }
                  }
                }
              }
            }
          }

          List<Run> runs = runsDao.findByPitcherIdAndGameId(playerId, gameId);
          if (runs != null) {
            for (Run run : runs) {
              r++;
              if (run.isEarnedFlg()) {
                er++;
              }
            }
          }

          tempMap.put("name", player.getName());
          tempMap.put("inning", this.formatOutCount(gotOuts));
          tempMap.put("er", er);
          tempMap.put("r", r);
          tempMap.put("k", k);
          tempMap.put("hit", hit);
          tempMap.put("hr", hr);
          tempMap.put("bb", bb);
          result.add(tempMap);
        }
      }
    }
    return result;
  }

  private String formatOutCount(int gotOuts) {
    int inning = gotOuts / 3;
    int additional = gotOuts % 3;
    String result = additional == 0 ? String.valueOf(inning) : inning + " " + additional + "/3";
    return result;
  }

  private String formatField(int fieldNumber) {
    String field = "";
    if (fieldNumber == 1) {
      field = "投";
    } else if (fieldNumber == 2) {
      field = "捕";
    } else if (fieldNumber == 3) {
      field = "一";
    } else if (fieldNumber == 4) {
      field = "二";
    } else if (fieldNumber == 5) {
      field = "三";
    } else if (fieldNumber == 6) {
      field = "遊";
    } else if (fieldNumber == 7) {
      field = "左";
    } else if (fieldNumber == 8) {
      field = "中";
    } else if (fieldNumber == 9) {
      field = "右";
    } else {
      field = "指";
    }
    return field;
  }
}
