package com.github.alvader01.Tests;

import com.github.alvader01.DAO.HuellaDAO;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class HuellaDAOTest {
    public static void main(String[] args) {
        HuellaDAO huellaDAO = new HuellaDAO();
        Usuario usuario = new Usuario();
        usuario.setId(8);

        System.out.println("===== INSERTANDO HUELLA =====");
        Huella huella = new Huella();
        huella.setIdUsuario(usuario);
        huella.setValor(BigDecimal.valueOf(10));
        huella.setUnidad("kg");  // Proporcionamos el valor para el campo unidad
        huellaDAO.addHuella(huella);

        // Buscar todas las huellas
        System.out.println("===== BUSCANDO TODAS LAS HUELLAS =====");
        List<Huella> huellas = huellaDAO.findall();
        for (Huella h : huellas) {
            System.out.println("Huella ID: " + h.getId());
        }

        // Buscar las huellas por ID de usuario
        System.out.println("===== BUSCANDO HUELLA POR ID DE USUARIO =====");
        List<Huella> huellasUsuario = huellaDAO.findByUserID(usuario);
        for (Huella h : huellasUsuario) {
            System.out.println("Huella ID: " + h.getId());
        }

        // Actualizar huella
        System.out.println("===== ACTUALIZANDO HUELLA =====");
        huella.setValor(BigDecimal.valueOf(15)); // Modificando el valor de la huella
        huellaDAO.updateHuella(huella);
        Huella updatedHuella = huellaDAO.findall().stream()
                .filter(h -> h.getId() == huella.getId())
                .findFirst().orElse(null);

        if (updatedHuella != null) {
            System.out.println("Huella actualizada:- Valor: " + updatedHuella.getValor());
        }

        // Eliminar huella
        /*System.out.println("===== ELIMINANDO HUELLA =====");
        huellaDAO.delete(huella);
        Huella deletedHuella = huellaDAO.findall().stream()
                .filter(h -> h.getId() == huella.getId())
                .findFirst().orElse(null);

        if (deletedHuella == null) {
            System.out.println("Huella eliminada correctamente.");
        } else {
            System.out.println("Error al eliminar huella.");
        }*/
    }
}

