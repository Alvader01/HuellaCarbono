package com.github.alvader01.Services;

import com.github.alvader01.Connection.Session;
import com.github.alvader01.DAO.UsuarioDAO;
import com.github.alvader01.Entities.Usuario;

public class UsuarioService {
    private UsuarioDAO userDAO = new UsuarioDAO();

    public boolean logUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty() ) {
            return false;
        }else{
            Usuario usuarioEncontrado = userDAO.findUserByEmail(user);
            if (usuarioEncontrado != null && user.getContraseña().equals(usuarioEncontrado.getContraseña())) {
                Session.getInstancia().logIn(usuarioEncontrado);
                System.out.println(usuarioEncontrado);
                return true;
            } else {
                System.out.println("CONTRASEÑA INCORRECTA");
                return false;
            }

        }
    }

    public boolean registerUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario usuarioExistente = userDAO.findUserByEmail(user);
            if (usuarioExistente != null) {
                System.out.println("El email ya está registrado.");
                return false;
            } else {
                userDAO.insertUsuario(user);
                Session.getInstancia().logIn(user);
                return true;
            }
        }
    }
}
