package ma.ensa.internHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
@Documented
public @interface ValidDate {
    String message() default "Date must be in the format MMM-YYYY or PRESENT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}