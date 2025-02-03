package com.github.alvader01.DAO;


import com.github.alvader01.Connection.Connection;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HuellaDAO {

    private static final String FINDALL = "FROM Huella";
    private static final String FINDBYUSERID = "FROM Huella WHERE idUsuario.id = :id";

    public void delete(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.delete(huella);
        session.getTransaction().commit();
        session.close();
    }

    public void updateHuella(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.merge(huella);
        session.getTransaction().commit();
        session.close();
    }


    public void insertaHuella(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.persist(huella);
        session.getTransaction().commit();
        session.close();
    }
    public List<Huella> findByUserID(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDBYUSERID);
        query.setParameter("id", usuario.getId());
        List<Huella> huellas = query.getResultList();
        session.close();
        return huellas;
    }

    public List<Huella> findall() {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDALL);
        List<Huella> huellas = query.getResultList();
        session.close();
        return huellas;

    }
}

