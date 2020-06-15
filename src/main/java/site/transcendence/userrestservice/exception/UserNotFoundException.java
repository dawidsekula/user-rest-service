package site.transcendence.userrestservice.exception;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 6076476507223367194L;

    public UserNotFoundException(String message){
        super(message);
    }

}
