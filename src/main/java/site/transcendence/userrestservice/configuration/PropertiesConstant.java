package site.transcendence.userrestservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConstant {

    public static Boolean REGISTRATION_CONFIRMATION_ENABLED;
    public static Boolean PASSWORD_RESET_ENABLED;

    @Value("${site.transcendence.mail.registration-confirmation-enabled:false}")
    private void setREGISTRATION_CONFIRMATION_ENABLED(Boolean value){
        REGISTRATION_CONFIRMATION_ENABLED = value;
    }

    @Value("${site.transcendence.mail.password-reset-enabled:false}")
    private void setPasswordResetEnabled(Boolean value){
        PASSWORD_RESET_ENABLED = value;
    }


}
