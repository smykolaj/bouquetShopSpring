package org.example.finalproject.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReviewConsistencyValidator.class)
@Documented
public @interface ReviewConsistency {
    String message() default
            "Comment contains positive words but rating is lower than 2";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}