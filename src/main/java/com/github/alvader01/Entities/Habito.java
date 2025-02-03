package com.github.alvader01.Entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "habito", schema = "huellacarbono")
public class Habito {
    @EmbeddedId
    private HabitoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private com.github.alvader01.Entities.Usuario idUsuario;

    @MapsId("idActividad")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad idActividad;

    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @Lob
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "ultima_fecha")
    private Instant ultimaFecha;

    public HabitoId getId() {
        return id;
    }

    public void setId(HabitoId id) {
        this.id = id;
    }

    public com.github.alvader01.Entities.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(com.github.alvader01.Entities.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(Instant ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

}