package ma.ensa.internHub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private static final String PRESENT = "PRESENT";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH);

    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        if (PRESENT.equalsIgnoreCase(value.trim())) {
            return true;
        }

        try {
            FORMATTER.parse(value.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
