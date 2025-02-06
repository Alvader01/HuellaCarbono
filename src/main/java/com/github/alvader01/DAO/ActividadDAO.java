package com.github.alvader01.DAO;

import com.github.alvader01.Connection.Connection;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Categoria;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Recomendacion;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class ActividadDAO {

    private final static String FINDALL = "FROM Actividad";
    private final static String FINDACTIVITYBYNAME = "FROM Actividad WHERE nombre = :name";
    private final static String FINDUNITBYACTIVITY = "SELECT c.unidad FROM Categoria c JOIN Actividad a ON a.idCategoria = c WHERE a.idCategoria.id = :idCategoria";
    private final static String FINDBYID = "FROM Actividad WHERE id = :id";
    private final static String FINDRECOMMENDATIONBYID = "SELECT r FROM Recomendacion r WHERE r.idCategoria = :categoria";


    public List<Recomendacion> findRecommendationByActivityId(int activityId) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Actividad actividad = session.get(Actividad.class, activityId);

        Categoria categoria = actividad.getIdCategoria();

        Query query = session.createQuery(FINDRECOMMENDATIONBYID);
        query.setParameter("categoria", categoria);
        List<Recomendacion> recomendaciones = query.getResultList();

        session.close();
        return recomendaciones;
    }

    public Actividad findByID(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDBYID);
        query.setParameter("id", huella.getIdActividad().getId());
        Actividad actividad = (Actividad) query.getSingleResult();
        session.close();
        return actividad;
    }

    public List<Actividad> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDALL);
        List<Actividad> actividades = query.getResultList();
        session.close();
        return actividades;
    }

    public Actividad findActivityByName(Actividad actividad) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDACTIVITYBYNAME);
        query.setParameter("name", actividad.getNombre());
        Actividad actividadEncontrada = (Actividad) query.getSingleResult();
        return actividadEncontrada;
    }

    public String findUnitByActivity(Actividad actividad) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDUNITBYACTIVITY);
        query.setParameter("idCategoria", actividad.getIdCategoria().getId());
        List<String> resultados = query.getResultList();
        String unidad = null;
        if (!resultados.isEmpty()) {
            unidad = resultados.get(0);
        }
        session.close();

        return unidad;
    }


}
