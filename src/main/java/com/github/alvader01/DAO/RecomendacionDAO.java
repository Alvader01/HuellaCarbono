package com.github.alvader01.DAO;


import com.github.alvader01.Connection.Connection;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Recomendacion;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RecomendacionDAO{

    private static final String FINDRECOMENDACIONESFORUSER = "SELECT r FROM Recomendacion r WHERE r.idCategoria.id = :idCategoria";


    public List <Recomendacion> findRecomendacionesForUser(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDRECOMENDACIONESFORUSER);
        query.setParameter("idCategoria", habito.getIdActividad().getIdCategoria().getId());
        List<Recomendacion> recomendaciones = query.getResultList();
        session.close();
        return recomendaciones;

    }
}