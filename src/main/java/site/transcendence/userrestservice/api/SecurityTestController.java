package site.transcendence.userrestservice.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.transcendence.userrestservice.configuration.PropertiesConstants;

@RestController
@RequestMapping("/test")
public class SecurityTestController {

    @GetMapping("/granted")
    public String accessForAuthorizedUsers() {
        return "Access granted for all";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public String accessForAdminRole() {
        return "Access granted for admin";
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/user")
    public String accessForUserRole() {
        return "Access granted for user";
    }

    @GetMapping
    public String test() {
        String message = "Value: " + PropertiesConstants.REGISTRATION_CONFIRMATION_ENABLED;

        return message;
    }

}
