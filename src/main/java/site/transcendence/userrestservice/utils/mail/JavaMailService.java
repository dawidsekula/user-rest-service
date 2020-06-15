package site.transcendence.userrestservice.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import site.transcendence.userrestservice.exception.SendingEmailException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static site.transcendence.userrestservice.common.MailMessages.*;

@Service
public class JavaMailService implements MailService{

    private JavaMailSender javaMailSender;

    public JavaMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRegistrationConfirmation(String email, String token){
        String htmlBodyWithToken = REGISTRATION_HTML_BODY.replace(TOKEN_URL_VALUE, token);
        String textBodyWithToken = REGISTRATION_TEXT_BODY.replace(TOKEN_URL_VALUE, token);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(email);
            messageHelper.setSubject(REGISTRATION_SUBJECT);
            messageHelper.setText(textBodyWithToken, htmlBodyWithToken);

            javaMailSender.send(message);
        }catch (MessagingException e){
            throw new SendingEmailException(e.getLocalizedMessage());
        }
    }

    @Override
    public void sendPasswordResetRequest(String username, String email, String token) {
        String htmlBodyWithToken = PASSWORD_RESET_HTML_BODY
                .replace(USERNAME_VALUE, username)
                .replace(TOKEN_URL_VALUE, token);
        String textBodyWithToken = PASSWORD_RESET_TEXT_BODY
                .replace(USERNAME_VALUE, username)
                .replace(TOKEN_URL_VALUE, token);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(email);
            messageHelper.setSubject(PASSWORD_RESET_SUBJECT);
            messageHelper.setText(textBodyWithToken, htmlBodyWithToken);

            javaMailSender.send(message);
        }catch (MessagingException e){
            throw new SendingEmailException(e.getLocalizedMessage());
        }
    }



}
