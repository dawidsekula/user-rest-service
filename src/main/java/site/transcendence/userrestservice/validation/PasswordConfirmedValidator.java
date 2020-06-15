package site.transcendence.userrestservice.validation;

import site.transcendence.userrestservice.api.requests.RequestWithPasswords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, RequestWithPasswords> {

    @Override
    public boolean isValid(RequestWithPasswords request, ConstraintValidatorContext context) {
        String password = request.getPassword();
        String confirmedPassword = request.getPasswordConfirmation();
        return password.equals(confirmedPassword);
    }

}
