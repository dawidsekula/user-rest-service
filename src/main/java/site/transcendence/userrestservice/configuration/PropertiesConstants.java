package site.transcendence.userrestservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConstants {

    public static Boolean REGISTRATION_CONFIRMATION_ENABLED;

    @Value("${site.transcendence.mail.registration-confirmation-enabled:false}")
    private void setREGISTRATION_CONFIRMATION_ENABLED(Boolean value){
        REGISTRATION_CONFIRMATION_ENABLED = value;
    }


}
