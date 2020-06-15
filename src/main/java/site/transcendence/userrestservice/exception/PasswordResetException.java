package site.transcendence.userrestservice.exception;

public class PasswordResetException extends RuntimeException {

    private static final long serialVersionUID = 2781709736122585489L;

    public PasswordResetException(String message) {
        super(message);
    }
}
