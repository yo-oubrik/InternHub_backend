package ma.ensa.internHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LinkedInUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLinkedInUrl {
    String message() default "Invalid LinkedIn URL format. Must start with 'https://www.linkedin.com/'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

