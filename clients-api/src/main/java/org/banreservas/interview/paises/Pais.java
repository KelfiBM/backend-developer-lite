package org.banreservas.interview.paises;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Pais extends PanacheEntity {
    @Column(unique = true, length = 3, nullable = false)
    public String codeCCA3;

    @Column(length = 100, nullable = false)
    public String name;

    @Column(length = 100)
    public String demonymM;

    @Column(length = 100)
    public String demonymF;

    @Column(nullable = false)
    public boolean deprecated;
}
