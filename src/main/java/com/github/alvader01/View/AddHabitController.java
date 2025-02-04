package com.github.alvader01.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import com.github.alvader01.App;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.HabitoId;
import com.github.alvader01.Services.ActividadService;
import com.github.alvader01.Services.HabitoService;
import com.github.alvader01.Connection.Session;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddHabitController extends Controller implements Initializable {

    @FXML
    private ComboBox<Actividad> activityComboBox;
    @FXML
    private TextField frequencyTextField; // Aquí es donde se ingresa la frecuencia
    @FXML
    private Button addHabitButton;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private DatePicker habitDatePicker;

    private final ActividadService activityService = new ActividadService();
    private final HabitoService habitService = new HabitoService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        activityComboBox.getItems().setAll(activityService.getAllActivities());
        activityComboBox.setCellFactory(createActivityCellFactory());
        activityComboBox.setButtonCell(createActivityButtonCell());
        typeComboBox.getItems().addAll("Semanal", "Diaria", "Mensual", "Anual");
        habitDatePicker.setValue(LocalDate.now());
        habitDatePicker.setDayCellFactory(createDateCellFactory());
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    private Callback<ListView<Actividad>, ListCell<Actividad>> createActivityCellFactory() {
        return param -> new ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getNombre() : null);
            }
        };
    }

    private ListCell<Actividad> createActivityButtonCell() {
        return new ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getNombre() : null);
            }
        };
    }

    private Callback<DatePicker, DateCell> createDateCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }

    private Habito gatherHabitData() {
        String type = typeComboBox.getValue();
        int frequency = 0;
        try {
            frequency = Integer.parseInt(frequencyTextField.getText());
        } catch (NumberFormatException e) {
            showErrorFrecuency();
            return null;
        }

        LocalDate habitDate = habitDatePicker.getValue();
        Actividad activity = activityService.getActivityByName(activityComboBox.getSelectionModel().getSelectedItem());


        boolean habitExists = habitService.checkHabitExists(activity, Session.getInstance().getUserLoged());
        if (habitExists) {
            showErrorHabitExists();
            return null;
        }


        Habito habit = new Habito();
        habit.setIdActividad(activity);
        habit.setFrecuencia(frequency);
        habit.setTipo(type);
        habit.setIdUsuario(Session.getInstance().getUserLoged());
        habit.setUltimaFecha(habitDate);
        HabitoId habitId = new HabitoId();
        habitId.setIdActividad(activity.getId());
        habitId.setIdUsuario(Session.getInstance().getUserLoged().getId());
        habit.setId(habitId);

        return habit;
    }


    public void saveHabit() throws IOException {
        Habito habit = gatherHabitData();
        if (habit != null) {
            habitService.addHabit(habit);
            showSavedHabit();
            changeSceneToMainPage();
        }
    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }

    private void showSavedHabit() {
        AppController.showSavedHabit();
    }

    private void showErrorFrecuency() {
        AppController.showErrorFrecuency();
    }
    private void showErrorHabitExists() {
        AppController.showErrorHabitExists();
    }

}
