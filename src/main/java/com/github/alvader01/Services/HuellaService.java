package com.github.alvader01.Services;

import com.github.alvader01.DAO.HuellaDAO;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class HuellaService {
    HuellaDAO huellaDAO = new HuellaDAO();

    public boolean addHuella(Huella huella) {
        if (huella.getUnidad() == null || huella.getUnidad().isEmpty()) {
            System.out.println("Error: 'unidad' está vacío o es nulo.");
            return false;
        }
        if (huella.getValor() == null || huella.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Error: 'valor' es nulo o no es mayor a cero.");
            return false;
        }
        if (huella.getIdActividad() == null) {
            System.out.println("Error: 'idActividad' está vacío o es nulo.");
            return false;
        }
        try {
            huellaDAO.insertaHuella(huella);
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar la huella en la base de datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHuella(Huella huella) {
        boolean aux = false;
        try {
            huellaDAO.updateHuella(huella);
            aux = true;
        } catch (Exception e) {
            System.err.println("Error actualizando la huella: " + e.getMessage());
        }
        return aux;
    }

    public List<Huella> findByUserID(Usuario usuario) {
        return huellaDAO.findByUserID(usuario);
    }

    public boolean deleteHuella(Huella huella) {
        boolean aux = false;
        try {
            huellaDAO.delete(huella);
            aux = true;
        } catch (Exception e) {
            System.err.println("Error actualizando la huella: " + e.getMessage());
        }
        return aux;
    }

}
