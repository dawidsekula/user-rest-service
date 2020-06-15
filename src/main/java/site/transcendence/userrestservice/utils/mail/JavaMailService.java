package site.transcendence.userrestservice.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import site.transcendence.userrestservice.api.users.UserDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static site.transcendence.userrestservice.common.MailMessages.*;

@Service
public class JavaMailService implements MailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationConfirmation(UserDTO user, String token){
        String htmlBodyWithToken = REGISTRATION_HTML_BODY.replace(TOKEN_URL_VALUE, token);
        String textBodyWithToken = REGISTRATION_TEXT_BODY.replace(TOKEN_URL_VALUE, token);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(REGISTRATION_SUBJECT);
            messageHelper.setText(textBodyWithToken, htmlBodyWithToken);

            javaMailSender.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }






}
