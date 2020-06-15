package site.transcendence.userrestservice.api.requests;

import lombok.Getter;
import lombok.Setter;
import site.transcendence.userrestservice.validation.PasswordConfirmed;
import site.transcendence.userrestservice.validation.PasswordPolicy;
import site.transcendence.userrestservice.validation.UniqueEmail;
import site.transcendence.userrestservice.validation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter

@PasswordConfirmed
public class UserCreateRequest extends RequestWithPasswords{

    @UniqueUsername
    @Size(min = 2, max = 20)
    private String username;
    @UniqueEmail
    @Size(max = 100)
    @Email
    @NotBlank
    private String email;

}
