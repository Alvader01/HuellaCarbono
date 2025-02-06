package com.github.alvader01.Utils;

import com.github.alvader01.Connection.Session;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Services.ActividadService;
import com.github.alvader01.Services.HuellaService;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSV {
    private static final HuellaService footprintService = new HuellaService();
    private static final ActividadService activityService = new ActividadService();

    public static void exportToCSV() {
        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Fecha,Actividad,Valor,Unidad\n");

                for (Huella footprint : footprints) {
                    Actividad activity = activityService.getActivityById(footprint);
                    String activityName = (activity != null) ? activity.getNombre() : "Actividad no disponible";
                    writer.write(String.format("%s,%s,%s,%s\n", footprint.getFecha(), activityName, footprint.getValor(), footprint.getUnidad()));
                }


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportación Exitosa");
                alert.setHeaderText("Huellas exportadas");
                alert.setContentText("Las huellas se han exportado correctamente a CSV.");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Exportar");
                alert.setContentText("Hubo un problema al exportar las huellas a CSV.");
                alert.showAndWait();
            }
        }
    }
}
