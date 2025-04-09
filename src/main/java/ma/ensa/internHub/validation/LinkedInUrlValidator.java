package ma.ensa.internHub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LinkedInUrlValidator implements ConstraintValidator<ValidLinkedInUrl, String> {
    
    @Override
    public void initialize(ValidLinkedInUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; 
        }
        return value.startsWith("https://www.linkedin.com/");
    }
}