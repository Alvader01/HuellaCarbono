package com.github.alvader01.Services;

import com.github.alvader01.DAO.ActividadDAO;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Huella;

import java.util.List;

public class ActividadService {
    ActividadDAO actividadDAO = new ActividadDAO();


    public List<Actividad> getAllActividades() {
        return actividadDAO.findAll();
    }

    public Actividad getActividadByName(Actividad actividad) {
        return actividadDAO.findActividadByName(actividad);
    }

    public String getUnidadByActividad(Actividad actividad) {
        return actividadDAO.findUnidadByActividad(actividad);
    }

    public Actividad getActividadById(Huella huella) {
        return actividadDAO.findByID(huella);
    }

}

