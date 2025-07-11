package org.banreservas.interview.clientes.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.banreservas.interview.clientes.validation.CorreoNotBeingUsed;
import org.banreservas.interview.clientes.validation.PaisExists;

public class CreateClienteDto {

    @NotBlank(message = "Debe proveer un primer nombre")
    @Size(max = 50, message = "Maximo 50 caracteres")
    public String primerNombre;

    @Size(max = 50, message = "Maximo 50 caracteres")
    public String segundoNombre;

    @NotBlank(message = "Debe proveer un primer appellido")
    @Size(max = 50, message = "Maximo 50 caracteres")
    public String primerApellido;

    @Size(max = 50, message = "Maximo 50 caracteres")
    public String segundoApellido;

    @NotBlank(message = "Debe proveer un correo")
    @Size(max = 50, message = "Maximo 50 caracteres")
    @Email(message = "Formato de correo invalido")
    @CorreoNotBeingUsed
    public String correo;

    @NotBlank(message = "Debe proveer una direccion")
    @Size(max = 200, message = "Maximo 200 caracteres")
    public String direccion;

    @NotBlank(message = "Debe proveer un telefono")
    @Size(max = 15, message = "Maximo 15 caracteres")
    @Digits(integer = 15, fraction = 0, message = "Solo digitos")
    public String telefono;

    @NotBlank(message = "Debe proveer un pais")
    @Size(min = 3, max = 3, message = "Debe cumplir con el formato de codigo ISO 3166 de 3 digitos")
    @PaisExists
    public String pais;
}
