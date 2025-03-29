package ma.ensa.internHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidNameValidator.class)
@Documented
public @interface ValidName {
    String message() default "Name must contain only letters and be between 2 and 50 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}