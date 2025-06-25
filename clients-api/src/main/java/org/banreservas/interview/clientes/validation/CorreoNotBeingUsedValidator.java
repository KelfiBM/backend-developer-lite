package org.banreservas.interview.clientes.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.banreservas.interview.clientes.ClienteService;

@ApplicationScoped
public class CorreoNotBeingUsedValidator implements ConstraintValidator<CorreoNotBeingUsed, String> {

    @Inject
    ClienteService clienteService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        var cliente = clienteService.findByCorreo(s);
        return cliente == null;
    }

}
