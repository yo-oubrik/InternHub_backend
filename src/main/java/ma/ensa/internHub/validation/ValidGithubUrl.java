package ma.ensa.internHub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GithubUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGithubUrl {
    String message() default "Invalid GitHub URL format. Must start with 'https://github.com/'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}