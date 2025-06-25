package org.banreservas.interview.clientes.filters;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteFilter {
    private final String paisCodeCCA3ParameterName = "pais.codeCCA3";
    private String paisCodeCCA3;

    public ClienteFilter paisCodeCCA3(String paiscodeCCA3) {
        this.paisCodeCCA3 = paiscodeCCA3;
        return this;
    }

    public String buildQuery() {
        var fields = new ArrayList<String>();
        if (paisCodeCCA3 != null) {
            fields.add(paisCodeCCA3ParameterName + " = :" + paisCodeCCA3ParameterName.replace(".", ""));
        }
        return String.join(" and ", fields);
    }

    public HashMap<String, Object> buildParameters() {
        var parameters = new HashMap<String, Object>();
        if (paisCodeCCA3 != null) {
            parameters.put(paisCodeCCA3ParameterName.replace(".", ""), paisCodeCCA3);
        }
        return parameters;
    }
}
