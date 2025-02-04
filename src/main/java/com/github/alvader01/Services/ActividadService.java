package com.github.alvader01.Services;

import com.github.alvader01.DAO.ActividadDAO;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Recomendacion;

import java.util.List;

public class ActividadService {
    ActividadDAO actividadDAO = new ActividadDAO();


    public List<Actividad> getAllActivities() {
        return actividadDAO.findAll();
    }

    public Actividad getActivityByName(Actividad actividad) {
        return actividadDAO.findActivityByName(actividad);
    }

    public String getUnitByActivity(Actividad actividad) {
        return actividadDAO.findUnitByActivity(actividad);
    }

    public Actividad getActivityById(Huella huella) {
        return actividadDAO.findByID(huella);
    }
    public String getRecommendationByActivity(Actividad actividad) {
        List<Recomendacion> recomendaciones = actividadDAO.findRecommendationByActivityId(actividad.getId());
        StringBuilder recomendacionesText = new StringBuilder();
        for (Recomendacion recomendacion : recomendaciones) {
            recomendacionesText.append("Descripción: ").append(recomendacion.getDescripcion()).append("\n");
            recomendacionesText.append("Impacto Estimado: ").append(recomendacion.getImpactoEstimado()).append("\n\n");
        }
        return recomendacionesText.toString();
    }

}

