package org.banreservas.interview.clientes;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.banreservas.interview.paises.Pais;

@Entity
public class Cliente extends PanacheEntity {

    @Column(length = 50, nullable = false)
    public String primerNombre;

    @Column(length = 50)
    public String segundoNombre;

    @Column(length = 50, nullable = false)
    public String primerApellido;

    @Column(length = 50)
    public String segundoApellido;

    @Column(length = 50, nullable = false, unique = true)
    public String correo;

    @Column(length = 200, nullable = false)
    public String direccion;

    @Column(length = 15, nullable = false)
    public String telefono;

    @ManyToOne(optional = false)
    public Pais pais;
}
