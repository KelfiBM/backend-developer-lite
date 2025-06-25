package org.banreservas.interview.clientes.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.banreservas.interview.clientes.validation.PaisMustExists;

public class UpdateClienteDto {

    @Size(max = 50, message = "Maximo 50 caracteres")
    @Email(message = "Formato de correo invalido")
    public String correo;

    @Size(max = 200, message = "Maximo 200 caracteres")
    public String direccion;

    @Size(max = 15, message = "Maximo 15 caracteres")
    @Digits(integer = 15, fraction = 0, message = "Solo digitos")
    public String telefono;

    @Size(min = 3, max = 3, message = "Debe cumplir con el codigo ISO 3166 de 3 digitos")
    @PaisMustExists
    public String pais;
}
