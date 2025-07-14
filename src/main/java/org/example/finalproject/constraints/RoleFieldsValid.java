package org.example.finalproject.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleFieldsValidator.class)
@Documented
public @interface RoleFieldsValid {

    String message() default
            "Missing fields required by the selected role(s)";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
