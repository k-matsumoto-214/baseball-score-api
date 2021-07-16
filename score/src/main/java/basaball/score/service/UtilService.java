package basaball.score.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
  public ResponseEntity<Object> responseFromObject(Object inputObject) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.serializeNulls().create();
    String json = gson.toJson(inputObject);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(json);
  }

  public ResponseEntity<Object> response() {
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
  }
}
