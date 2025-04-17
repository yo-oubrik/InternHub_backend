package ma.ensa.internHub.exception;

public class TokenAlreadySentException extends RuntimeException {
    private final long secondsRemaining;

    public TokenAlreadySentException(String message, long secondsRemaining) {
        super(message);
        this.secondsRemaining = secondsRemaining;
    }

    public long getSecondsRemaining() {
        return secondsRemaining;
    }
}
