package site.transcendence.userrestservice.api.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserLogInRequest {

    private String username;
    private String password;

}
