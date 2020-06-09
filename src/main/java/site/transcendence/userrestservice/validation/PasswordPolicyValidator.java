package site.transcendence.userrestservice.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyValidator implements ConstraintValidator<PasswordPolicy, String> {

    //TODO: FOR TESTING ONLY
    private static final int MINIMUM_LENGTH = 1;
    private static final int MAXIMUM_LENGTH = 50;
    //TODO: FOR TESTING ONLY
    private static final int MINIMUM_RULES_TO_FULFILL = 1;
    private static final int MINIMUM_LOWERCASE = 1;
    private static final int MINIMUM_UPPERCASE = 1;
    private static final int MINIMUM_DIGITS = 1;
    private static final int MINIMUM_SPECIAL = 1;
    private static final int MAXIMUM_SAME_NEIGHBOURING_CHARACTERS = 3;


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        List<Rule> passwordRules = new ArrayList<>();

        CharacterCharacteristicsRule passwordChars = new CharacterCharacteristicsRule(
                MINIMUM_RULES_TO_FULFILL,
                new CharacterRule(EnglishCharacterData.LowerCase, MINIMUM_LOWERCASE),
                new CharacterRule(EnglishCharacterData.UpperCase, MINIMUM_UPPERCASE),
                new CharacterRule(EnglishCharacterData.Digit, MINIMUM_DIGITS),
                new CharacterRule(EnglishCharacterData.Special, MINIMUM_SPECIAL));

        passwordRules.add(new LengthRule(MINIMUM_LENGTH, MAXIMUM_LENGTH));
        passwordRules.add(new RepeatCharacterRegexRule(MAXIMUM_SAME_NEIGHBOURING_CHARACTERS));
        passwordRules.add(passwordChars);

        PasswordValidator passwordValidator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult ruleResult = passwordValidator.validate(passwordData);

        return ruleResult.isValid();
    }


}
