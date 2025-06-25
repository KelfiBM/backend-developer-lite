package org.banreservas.interview.clientes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaisExistsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaisExists {
    String message() default "Pais no encontrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
