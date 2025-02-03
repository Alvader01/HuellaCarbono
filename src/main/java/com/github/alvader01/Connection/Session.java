package com.github.alvader01.Connection;


import com.github.alvader01.Entities.Usuario;

public class Session {
    private static Session _instance;
    private static Usuario userLoged;


    private Session() {
    }

    public static Session getInstancia() {
        if (_instance == null) {
            _instance = new Session();
            _instance.logIn(userLoged);
        }
        return _instance;
    }

    public void logIn(Usuario user) {
        userLoged = user;
    }

    public Usuario getUsuarioIniciado() {
        return userLoged;
    }

    public void logOut() {
        userLoged = null;
    }
}
