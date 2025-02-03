package com.github.alvader01.Connection;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static Connection instance;
    private SessionFactory sessionFactory;

    private Connection( ){
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al construir la sessionFactory");
        }
    }
    public static Connection getInstance(){
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
    public void close(){
        if(sessionFactory != null && sessionFactory.isOpen()){
            sessionFactory.close();
        }
    }
}
