package basaball.score.controller;

import basaball.score.controller.exception.DuplicateAccountIdException;
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
}
