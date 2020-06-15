package site.transcendence.userrestservice.utils.mail;

import site.transcendence.userrestservice.api.users.UserDTO;

public interface MailService {

    void sendRegistrationConfirmation(UserDTO user, String token);

}
