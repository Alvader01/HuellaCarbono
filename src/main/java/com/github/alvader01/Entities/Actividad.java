package com.github.alvader01.Entities;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actividad", schema = "huellacarbono")
public class Actividad {
    @Id
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private com.github.alvader01.Entities.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.alvader01.Entities.Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.alvader01.Entities.Huella> huellas = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public com.github.alvader01.Entities.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(com.github.alvader01.Entities.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<com.github.alvader01.Entities.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<com.github.alvader01.Entities.Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<com.github.alvader01.Entities.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<com.github.alvader01.Entities.Huella> huellas) {
        this.huellas = huellas;
    }
    @Override
    public String toString() {
        if (idCategoria != null && idCategoria.getId() == null) {
            Hibernate.initialize(idCategoria);
        }

        return "Actividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idCategoria=" + (idCategoria != null ? idCategoria.getId() : "Sin categoría") +
                ", habitos=" + habitos +
                ", huellas=" + huellas +
                '}';
    }


}