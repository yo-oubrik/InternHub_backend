package ma.ensa.internHub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GithubUrlValidator implements ConstraintValidator<ValidGithubUrl, String> {
    
    @Override
    public void initialize(ValidGithubUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; 
        }
        return value.startsWith("https://github.com/");
    }
}