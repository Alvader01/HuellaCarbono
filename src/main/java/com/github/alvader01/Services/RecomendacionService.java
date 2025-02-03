package com.github.alvader01.Services;

import com.github.alvader01.DAO.RecomendacionDAO;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Recomendacion;

import java.util.List;

public class RecomendacionService {
    RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    public List<Recomendacion> findRecomendacionesForUser(List <Habito> habitos, List<Recomendacion> recomendaciones) {
        if (habitos == null) {
            return null;
        } else {
            for (Habito habito : habitos) {
                recomendaciones.addAll(recomendacionDAO.findRecomendacionesForUser(habito));
            }
            return recomendaciones;
        }

    }
}
