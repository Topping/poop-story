package poop.story.backend.application.exception;

public class PersistFailedException extends RuntimeException {
    public PersistFailedException(String message) {
        super(message);
    }
}
