package ma.ensa.internHub.exception;

public class EmptyResourcesException extends RuntimeException {
    public EmptyResourcesException(String message) {
        super(message);
    }
}