package com.github.alvader01.Services;

import com.github.alvader01.Connection.Session;
import com.github.alvader01.DAO.UsuarioDAO;
import com.github.alvader01.Entities.Usuario;
import com.github.alvader01.Utils.PasswordHasher;
import com.github.alvader01.View.AppController;

import java.math.BigDecimal;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO userDAO = new UsuarioDAO();

    public boolean loginUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario foundUser = userDAO.findUserByEmail(user);
            if (foundUser != null) {
                String hashedInputPassword = PasswordHasher.hashPassword(user.getContraseña());
                if (hashedInputPassword.equals(foundUser.getContraseña())) {
                    Session.getInstance().logIn(foundUser);
                    System.out.println(foundUser);
                    return true;
                } else {
                    System.out.println("CONTRASEÑA INCORRECTA");
                    return false;
                }
            } else {
                System.out.println("EMAIL NO ENCONTRADO");
                return false;
            }
        }
    }

    public boolean registerUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario existingUser = userDAO.findUserByEmail(user);
            if (existingUser != null) {
                AppController.ShowAlertsEmailExists();
                return false;
            } else {
                userDAO.insertUsuario(user);
                Session.getInstance().logIn(user);
                return true;
            }
        }
    }

    public List<BigDecimal> getEmissionFactor(Usuario user) {
        return userDAO.getFactorEmision(user);
    }

    public boolean updateUser(Usuario user) {
        userDAO.updateUsuario(user);
        return true;
    }
}