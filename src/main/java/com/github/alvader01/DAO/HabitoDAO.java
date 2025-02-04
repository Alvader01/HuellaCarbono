package com.github.alvader01.DAO;


import com.github.alvader01.Connection.Connection;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HabitoDAO {
    public static final String FINDALL = "FROM Habito";
    public static final String FINDBYUSER = "FROM Habito WHERE idUsuario.id = :id";
    public static final String FINDACTIVITYNAME = "FROM Actividad WHERE id = :idActividad";
    public static final String CHECK_DUPLICATE = "FROM Habito WHERE idUsuario.id = :idUsuario AND idActividad.id = :idActividad";


    public Actividad findActivityById(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDACTIVITYNAME);
        query.setParameter("idActividad", habito.getIdActividad().getId());
        Actividad actividadEncontrada = (Actividad) query.getSingleResult();
        session.close();
        return actividadEncontrada;
    }

    public boolean habitExists(int idUsuario, int idActividad) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(CHECK_DUPLICATE);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("idActividad", idActividad);
        List<Habito> results = query.getResultList();
        session.close();
        return !results.isEmpty();
    }


    public void addHabit(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.save(habito);
        session.getTransaction().commit();
        session.close();
    }


    public void update(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.update(habito);
        session.getTransaction().commit();
        session.close();
    }


    public List<Habito> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDALL);
        List<Habito> habitos = query.getResultList();
        session.close();
        return habitos;
    }


    public List<Habito> findByUser(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDBYUSER);
        query.setParameter("id", user.getId());
        List<Habito> habitos = query.getResultList();
        session.close();
        return habitos;
    }


    public boolean delete(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.delete(habito);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
