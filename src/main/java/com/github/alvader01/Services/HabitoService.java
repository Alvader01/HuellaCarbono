package com.github.alvader01.Services;

import com.github.alvader01.DAO.HabitoDAO;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HabitoService {
    HabitoDAO habitoDAO = new HabitoDAO();

    public boolean addHabit(Habito habito) {
        if (habitoDAO.habitExists(habito.getIdUsuario().getId(), habito.getIdActividad().getId())) {
            return false;
        }
        habitoDAO.addHabit(habito);
        return true;
    }

    public Actividad getActivityById(Habito habito) {
        return habitoDAO.findActivityById(habito);
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

    public boolean deleteHabit(Habito habito) {
        habitoDAO.delete(habito);
        return true;
    }
    public boolean updateHabit(Habito habito) {
        habitoDAO.update(habito);
        return true;
    }
    public boolean checkHabitExists(Actividad activity, Usuario user) {
        HabitoDAO habitDAO = new HabitoDAO();
        return habitDAO.habitExists(user.getId(), activity.getId());
    }

}
