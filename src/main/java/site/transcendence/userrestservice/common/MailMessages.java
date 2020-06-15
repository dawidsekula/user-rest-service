package site.transcendence.userrestservice.common;

import org.springframework.stereotype.Component;
import site.transcendence.userrestservice.security.SecurityConstant;

@Component
public class MailMessages {

    // Application server location
    public static final String SERVER_LOCATION = "http://localhost:8080";

    // Registration confirmation request URL
    public static final String EMAIL_VERIFICATION_URL = SecurityConstant.EMAIL_VERIFICATION_URL;

    // Password reset request URL
    public static final String PASSWORD_RESET_URL = SecurityConstant.PASSWORD_RESET_URL;

    // Token URL param
    public static final String TOKEN_URL_PARAMETER = "?token=";

    // Token URL value
    public static final String TOKEN_URL_VALUE = "$tokenValue";

    // Username value
    public static final String USERNAME_VALUE = "$usernameValue";

    // Registration subject line for email;
    public static final String REGISTRATION_SUBJECT = "One last step to complete your registration!";

    // HTML body for the email;
    public static final String REGISTRATION_HTML_BODY = "<h1>Please verify your email address.</h1>" +
            "<p>Thank you for registering. To complete registration process please open following URL: " +
            "<a href='" + SERVER_LOCATION + EMAIL_VERIFICATION_URL + TOKEN_URL_PARAMETER + TOKEN_URL_VALUE + "'>" +
            "Verify your email</a><br/><br/>" +
            "Thank you!";

    //Email body for non-HTML email clients;
    public static final String REGISTRATION_TEXT_BODY = "Please verify your email address. " +
            "Thank you for registering. To complete registration process please open following URL: " +
            SERVER_LOCATION + EMAIL_VERIFICATION_URL + TOKEN_URL_PARAMETER + TOKEN_URL_VALUE +
            " Thank you!";

    //Password reset subject line for email;
    public static final String PASSWORD_RESET_SUBJECT = "Password reset requested";

    //HTML body for the email;
    public static final String PASSWORD_RESET_HTML_BODY = "<h1>A request to reset your password</h1> " +
            "<p>Hi " + USERNAME_VALUE + " <br/>" +
            "Someone has requested to reset your password to our service. If it was not you please ignore this message, " +
            "otherwise please click on the link below to set a new password: " +
            "<a href='" + SERVER_LOCATION + PASSWORD_RESET_URL + TOKEN_URL_PARAMETER + TOKEN_URL_VALUE + "'>" +
            "Click this link to reset your password" + "</a><br/><br/>" +
            "Thank you!";

    //Email body for non-HTML email clients;
    public static final String PASSWORD_RESET_TEXT_BODY = "A request to reset your password " +
            "Hi " + USERNAME_VALUE +
            "! Someone has requested to reset your password to our service. If it was not you please ignore this message, " +
            "otherwise please click on the link below to set a new password: " +
            SERVER_LOCATION + PASSWORD_RESET_URL + TOKEN_URL_PARAMETER + TOKEN_URL_VALUE +
            " Thank you!";


}
