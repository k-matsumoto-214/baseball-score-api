package basaball.score.service;

import basaball.score.controller.exception.RegistrationException;
import basaball.score.dao.EventsDao;
import basaball.score.entity.Event;
import java.util.LinkedHashMap;
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
}
