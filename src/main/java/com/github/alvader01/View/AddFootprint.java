package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Connection.Session;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Services.ActividadService;
import com.github.alvader01.Services.HuellaService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddFootprint extends Controller implements Initializable {

    @FXML
    private Button btnSaveFootprint;
    @FXML
    private DatePicker dpDate;
    @FXML
    private ImageView imgBack;
    @FXML
    private ImageView imgInfo;
    @FXML
    private ComboBox<Actividad> cbActivities;
    @FXML
    private TextField tfValue;

    private final ActividadService actividadService = new ActividadService();
    private final HuellaService huellaServices = new HuellaService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureActivitiesComboBox();
        configureDatePicker();
    }

    private void configureActivitiesComboBox() {
        cbActivities.getItems().setAll(actividadService.getAllActivities());

        cbActivities.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Actividad> call(ListView<Actividad> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Actividad item, boolean empty) {
                        super.updateItem(item, empty);
                        setText((item != null) ? item.getNombre() : null);
                    }
                };
            }
        });

        cbActivities.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getNombre() : null);
            }
        });
    }

    private void configureDatePicker() {
        dpDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }

    private Huella collectFootprintData() {
        Huella footprint = new Huella();

        try {
            String valueText = tfValue.getText();
            if (valueText != null && !valueText.trim().isEmpty()) {
                footprint.setValor(new BigDecimal(valueText));
            } else {
                footprint.setValor(null);
            }
        } catch (NumberFormatException e) {
            showErrorValue();
            footprint.setValor(null);
        }

        Actividad selectedActivity = cbActivities.getSelectionModel().getSelectedItem();
        if (selectedActivity == null) {
            showErrorActivityNotSelected();
            footprint.setIdActividad(null);
        } else {
            footprint.setIdActividad(actividadService.getActivityByName(selectedActivity));
            footprint.setUnidad(actividadService.getUnitByActivity(selectedActivity));
        }

        if (dpDate.getValue() == null || dpDate.getValue().isAfter(LocalDate.now())) {
            footprint.setFecha(null);
        } else {
            footprint.setFecha(dpDate.getValue());
        }

        footprint.setIdUsuario(Session.getInstance().getUserLoged());


        boolean footprintExists = huellaServices.checkFootprintExists(selectedActivity, Session.getInstance().getUserLoged(), footprint.getFecha());
        if (footprintExists) {
            showErrorFootprintExists();
            resetFootprintForm();
            return null;
        }

        return footprint;
    }
    private void resetFootprintForm() {
        tfValue.clear();
        cbActivities.getSelectionModel().clearSelection();
        dpDate.setValue(null);
    }

    public void saveFootprint() throws IOException {
        Huella footprint = collectFootprintData();
        boolean result = huellaServices.addHuella(footprint);

        if (result) {
            showFootprintSaved();
            changeSceneToMainPage();
        } else {
            showErrorFootprintNotSaved();
        }
    }

    public void showInfo() {
        AppController.ShowInformationValue();
    }
    public void showErrorValue() {
        AppController.ShowErrorValue();
    }
    public void showErrorActivityNotSelected() {
        AppController.showErrorActivityNotSelected();
    }
    public void showFootprintSaved() {
        AppController.showFootprintSaved();
    }
    public void showErrorFootprintNotSaved() {
        AppController.showErrorFootprintNotSaved();
    }

    public void showErrorFootprintExists() {
        AppController.showErrorFootprintExists();
    }




}
