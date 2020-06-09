package site.transcendence.userrestservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SecurityTestController {

    @GetMapping("/granted")
    public String accessForAuthorizedUsers(){
        return "Access granted";
    }

    @GetMapping("/admin")
    public String accessForAdminRole(){
        return "Access granted";
    }

    @GetMapping("/user")
    public String accessForUserRole(){
        return "Access granted";
    }

}
