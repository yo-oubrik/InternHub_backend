package ma.ensa.internHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SalaryValidator.class)
public @interface Salary {
    String message() default "Salary must be greater than 500 or null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}