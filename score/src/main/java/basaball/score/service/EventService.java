package basaball.score.service;

import basaball.score.controller.exception.DataNotFoundException;
import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.EventsDao;
import basaball.score.entity.Event;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  @Autowired
  EventsDao eventsDao;

  public Map<String, Object> create(Event event) throws RegistrationException {
    if (eventsDao.create(event) != 1) {
      throw new RegistrationException("イベント登録に失敗しました。");
    }
    int id = eventsDao.fetchLastInsertId();
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("id", id);
    return result;
  }

  public List<Map<String, Object>> findByAtBatId(int atBatId, int teamId) throws DataNotFoundException {
    List<Event> events = eventsDao.findByAtBatId(atBatId, teamId);
    if (events == null) {
      throw new DataNotFoundException("イベント情報が見つかりません。");
    }

    List<Map<String, Object>> result = new ArrayList<>();

    for (Event event : events) {
      Map<String, Object> tempMap = new LinkedHashMap<>();
      tempMap.put("id", event.getId());
      tempMap.put("teamId", event.getTeamId());
      tempMap.put("resultOutCount", event.getResultOutCount());
      tempMap.put("resultFirstRunnerId", event.getResultFirstRunnerId());
      tempMap.put("resultSecondRunnerId", event.getResultSecondRunnerId());
      tempMap.put("resultThirdRunnerId", event.getResultThirdRunnerId());
      tempMap.put("comment", event.getComment());
      tempMap.put("timing", event.getTiming());
      tempMap.put("eventType", event.getEventType());
      result.add(tempMap);
    }

    return result;
  }
}
