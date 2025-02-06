package com.github.alvader01.Utils;

import com.github.alvader01.Connection.Session;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Services.ActividadService;
import com.github.alvader01.Services.HuellaService;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExportPDF {

    private static final HuellaService footprintService = new HuellaService();
    private static final ActividadService activityService = new ActividadService();


    public static void exportToPDF() {
        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.beginText();
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(50, 750);

                    contentStream.showText("Huellas de Carbono - Reporte PDF");
                    contentStream.newLine();
                    contentStream.showText("====================================");
                    contentStream.newLine();

                    for (Huella footprint : footprints) {
                        Actividad activity = activityService.getActivityById(footprint);
                        String activityName = (activity != null) ? activity.getNombre() : "Actividad no disponible";
                        contentStream.showText(String.format("Fecha: %s", footprint.getFecha()));
                        contentStream.newLine();
                        contentStream.showText(String.format("Actividad: %s", activityName));
                        contentStream.newLine();
                        contentStream.showText(String.format("Valor: %s %s", footprint.getValor(), footprint.getUnidad()));
                        contentStream.newLine();
                        contentStream.showText("====================================");
                        contentStream.newLine();
                    }

                    contentStream.endText();
                }

                document.save(file);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportación Exitosa");
                alert.setHeaderText("Huellas exportadas");
                alert.setContentText("Las huellas se han exportado correctamente a PDF.");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Exportar");
                alert.setContentText("Hubo un problema al exportar las huellas a PDF.");
                alert.showAndWait();
            }
        }
    }
}
