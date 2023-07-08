package poop.story.backend.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import poop.story.backend.application.exception.MissingSubjectException;
import poop.story.backend.application.exception.PersistFailedException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = MissingSubjectException.class)
    public ResponseEntity<Object> missingSubjectException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = PersistFailedException.class)
    public ResponseEntity<Object> persistFailedException(PersistFailedException exception) {
        return exception.userError()
            ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage())
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
