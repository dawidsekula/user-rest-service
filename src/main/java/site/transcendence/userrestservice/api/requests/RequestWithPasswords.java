package site.transcendence.userrestservice.api.requests;

import lombok.Getter;
import lombok.Setter;
import site.transcendence.userrestservice.validation.PasswordConfirmed;
import site.transcendence.userrestservice.validation.PasswordPolicy;

@Getter
@Setter

@PasswordConfirmed
public abstract class RequestWithPasswords {

    @PasswordPolicy
    protected String password;
    protected String passwordConfirmation;

}
