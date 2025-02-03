package com.github.alvader01.Services;

import com.github.alvader01.DAO.HabitoDAO;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HabitoService {
    HabitoDAO habitoDAO = new HabitoDAO();

    public boolean addHabito(Habito habito) {
        habitoDAO.insertarHabito(habito);
        return true;
    }

    public Actividad getActividadById(Habito habito) {
        return habitoDAO.findActividadById(habito);
    }

    public List<Habito> findByUser(Usuario user) {
        List<Habito> habitos = new ArrayList<>();
        if (user != null) {
            habitos = habitoDAO.findByUser(user);
            return habitos;
        } else {
            return null;
        }

    }

    public boolean deleteHabito(Habito habito) {
        habitoDAO.delete(habito);
        return true;
    }
}
