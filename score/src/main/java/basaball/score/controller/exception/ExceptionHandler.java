package basaball.score.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateAccountIdException.class)
  public ResponseEntity<Object> handleDuplicateAccountIdException(DuplicateAccountIdException e) {
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(null);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException e) {
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
  }
}
