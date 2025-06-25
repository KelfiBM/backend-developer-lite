package org.banreservas.interview.clientes.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.banreservas.interview.paises.PaisService;

@ApplicationScoped
public class PaisExistsValidator implements ConstraintValidator<PaisExists, String> {

    @Inject
    PaisService paisService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        var pais = paisService.getPaisByCCN3(s);
        return pais != null;
    }

}
