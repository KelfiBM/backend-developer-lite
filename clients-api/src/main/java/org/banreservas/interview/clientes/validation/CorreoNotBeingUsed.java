package org.banreservas.interview.clientes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CorreoNotBeingUsedValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorreoNotBeingUsed {
    String message() default "Correo se encuentra utilizado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
