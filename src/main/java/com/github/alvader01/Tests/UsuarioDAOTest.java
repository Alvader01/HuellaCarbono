package com.github.alvader01.Tests;

import com.github.alvader01.DAO.UsuarioDAO;
import com.github.alvader01.Entities.Usuario;

public class UsuarioDAOTest {
    public static void main(String[] args) {
        UsuarioDAO userDAO = new UsuarioDAO();


        System.out.println("===== INSERTANDO USUARIO =====");
        Usuario user = new Usuario();
        user.setNombre("Alvader");
        user.setEmail("alvader@email.com");
        user.setContraseña("123456");
        userDAO.insertUsuario(user);

        System.out.println("===== BUSCANDO USUARIO POR EMAIL =====");
        Usuario foundUser = userDAO.findUserByEmail(user);
        if (foundUser != null) {
            System.out.println("Usuario encontrado: " + foundUser.getNombre() + " - " + foundUser.getEmail());
        } else {
            System.out.println("Usuario NO encontrado.");
        }

        /*System.out.println("===== ACTUALIZANDO USUARIO =====");
        user.setNombre("Prueba Modificada");
        userDAO.updateUsuario(user);
        Usuario updatedUser = userDAO.findUserByEmail(user);
        System.out.println("Nombre actualizado: " + updatedUser.getNombre());

        System.out.println("===== ELIMINANDO USUARIO =====");
        userDAO.deleteUsuario(user);
        Usuario deletedUser = userDAO.findUserByEmail(user);
        if (deletedUser == null) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar usuario.");
        }*/
    }
}