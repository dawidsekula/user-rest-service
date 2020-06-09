package site.transcendence.userrestservice.validation;

import site.transcendence.userrestservice.api.requests.UserCreateRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, Object> {

    @Override
    public boolean isValid(Object user, ConstraintValidatorContext context) {
        String password = ((UserCreateRequest)user).getPassword();
        String confirmedPassword = ((UserCreateRequest)user).getPasswordConfirmation();
        return password.equals(confirmedPassword);
    }

}