package site.transcendence.userrestservice.utils.mail;

public interface MailService {

    void sendRegistrationConfirmation(String email, String token);

    void sendPasswordResetRequest(String username, String email, String token);


}
