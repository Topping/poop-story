package poop.story.backend.application.exception;

public class PersistFailedException extends RuntimeException {
    private final boolean userError;
    public PersistFailedException(String message, boolean userError) {
        super(message);
        this.userError = true;
    }

    public static PersistFailedException userError(String message) {
        return new PersistFailedException(message, true);
    }

    public static PersistFailedException serverError(String message) {
        return new PersistFailedException(message, false);
    }

    public boolean userError() {
        return userError;
    }
}
