package com.github.alvader01.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categoria", schema = "huellacarbono")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "factor_emision", nullable = false, precision = 10, scale = 4)
    private BigDecimal factorEmision;

    @Column(name = "unidad", nullable = false, length = 50)
    private String unidad;

    @OneToMany(mappedBy = "idCategoria")
    private Set<Actividad> actividads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCategoria")
    private Set<com.github.alvader01.Entities.Recomendacion> recomendacions = new LinkedHashSet<>();

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

    public BigDecimal getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(BigDecimal factorEmision) {
        this.factorEmision = factorEmision;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Set<Actividad> getActividads() {
        return actividads;
    }

    public void setActividads(Set<Actividad> actividads) {
        this.actividads = actividads;
    }

    public Set<com.github.alvader01.Entities.Recomendacion> getRecomendacions() {
        return recomendacions;
    }

    public void setRecomendacions(Set<com.github.alvader01.Entities.Recomendacion> recomendacions) {
        this.recomendacions = recomendacions;
    }

}