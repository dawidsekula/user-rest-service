package site.transcendence.userrestservice.exception;

public class SendingEmailException extends RuntimeException{

    private static final long serialVersionUID = 2146265792185848415L;

    public SendingEmailException(String message) {
        super(message);
    }
}
