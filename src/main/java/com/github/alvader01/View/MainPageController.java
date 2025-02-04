package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Connection.Session;
import com.github.alvader01.Entities.Habito;
import com.github.alvader01.Entities.Huella;
import com.github.alvader01.Entities.Actividad;
import com.github.alvader01.Entities.Recomendacion;
import com.github.alvader01.Services.ActividadService;
import com.github.alvader01.Services.HabitoService;
import com.github.alvader01.Services.HuellaService;
import com.github.alvader01.Services.UsuarioService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageController extends Controller implements Initializable {

    @FXML private Text text;
    @FXML private ImageView logOut;
    @FXML private Button buttonAddFingerprint;
    @FXML private ImageView profile;
    @FXML private Button buttonAddHabit;
    @FXML private Button buttonRecommendations;
    @FXML private TableView<Huella> footprintTableView;
    @FXML private TableColumn<Huella, BigDecimal> valueColumn;
    @FXML private TableColumn<Huella, String> unitColumn;
    @FXML private TableColumn<Huella, String> dateColumn;
    @FXML private TableColumn<Huella, String> activityColumn;
    @FXML private TableColumn<Huella, Void> deleteColumn;
    @FXML private TableView<Habito> habitTableView;
    @FXML private TableColumn<Habito, Integer> frequencyColumn;
    @FXML private TableColumn<Habito, String> typeColumn;
    @FXML private TableColumn<Habito, String> dateHabitColumn;
    @FXML private TableColumn<Habito, String> activityHabitColumn;
    @FXML private TableColumn<Habito, Void> deleteHabitColumn;
    @FXML private PieChart pieChart;
    @FXML private ImageView exportImage;

    private final HuellaService footprintService = new HuellaService();
    private final HabitoService habitService = new HabitoService();
    private final ActividadService activityService = new ActividadService();
    private final UsuarioService userService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUserGreeting();
        setupFootprintTableView();
        setupHabitTableView();
        setupPieChart();
    }

    private void initializeUserGreeting() {
        text.setText("Hola " + Session.getInstance().getUserLoged().getNombre() + "!");
    }

    private void setupFootprintTableView() {
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        activityColumn.setCellValueFactory(cellData -> getActivityName(cellData.getValue()));

        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());

        footprintTableView.getItems().setAll(footprints);
        footprintTableView.setEditable(true);
        setupEditableTableView();
        setupDeleteButton(deleteColumn, footprintTableView, footprintService);
    }
    public void exportFile() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccionar formato de exportación");
        alert.setHeaderText("Elige el formato para exportar:");
        alert.setContentText("Selecciona uno de los siguientes formatos:");

        ButtonType csvButton = new ButtonType("CSV");
        ButtonType pdfButton = new ButtonType("PDF");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(csvButton, pdfButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == csvButton) {

                exportToCSV();
            } else if (response == pdfButton) {

                exportToPDF();
            }
        });
    }

    private void setupHabitTableView() {
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        dateHabitColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));
        activityHabitColumn.setCellValueFactory(cellData -> getActivityNameForHabit(cellData.getValue()));

        List<Habito> habits = habitService.findByUser(Session.getInstance().getUserLoged());

        habitTableView.getItems().setAll(habits);
        habitTableView.setEditable(true);

        frequencyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        frequencyColumn.setOnEditCommit(event -> {
            Habito habit = event.getRowValue();
            Integer newFrequency = event.getNewValue();
            habit.setFrecuencia(newFrequency);
            habitService.updateHabit(habit);
            refreshHabitTableData();
        });

        setupDeleteButton(deleteHabitColumn, habitTableView, habitService);
    }

    private void refreshHabitTableData() {
        List<Habito> habits = habitService.findByUser(Session.getInstance().getUserLoged());
        habitTableView.getItems().setAll(habits);
    }

    private SimpleStringProperty getActivityName(Huella footprint) {
        Actividad activity = activityService.getActivityById(footprint);
        return new SimpleStringProperty(activity != null ? activity.getNombre() : "Actividad no disponible");
    }

    private SimpleStringProperty getActivityNameForHabit(Habito habit) {
        Actividad activity = habitService.getActivityById(habit);
        return new SimpleStringProperty(activity != null ? activity.getNombre() : "Actividad no disponible");
    }

    private void setupEditableTableView() {
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.BigDecimalStringConverter()));
        valueColumn.setOnEditCommit(event -> {
            Huella footprint = event.getRowValue();
            try {
                BigDecimal newValue = new BigDecimal(event.getNewValue().toString());
                footprint.setValor(newValue);
                footprintService.updateHuella(footprint);
                refreshTableData();
                setupPieChart();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }

    private <T> void setupDeleteButton(TableColumn<T, Void> column, TableView<T> tableView, Object service) {
        column.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");
            {
                deleteButton.setOnAction(event -> {
                    T item = getTableView().getItems().get(getIndex());
                    if (service instanceof HuellaService) {
                        ((HuellaService) service).deleteHuella((Huella) item);
                    } else if (service instanceof HabitoService) {
                        ((HabitoService) service).deleteHabit((Habito) item);
                    }
                    tableView.getItems().remove(item);
                    refreshTableData();
                    setupPieChart();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private void setupPieChart() {
        pieChart.getData().clear();
        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());
        List<BigDecimal> emissionFactors = userService.getEmissionFactor(Session.getInstance().getUserLoged());

        double total = footprints.stream().mapToDouble(h -> h.getValor().doubleValue()).sum();

        for (int i = 0; i < footprints.size(); i++) {
            Huella footprint = footprints.get(i);
            Actividad activity = activityService.getActivityById(footprint);
            String activityName = (activity != null) ? activity.getNombre() : "No tienes actividades registradas";
            BigDecimal emissionValue = (i < emissionFactors.size()) ? emissionFactors.get(i) : BigDecimal.ZERO;
            double value = emissionValue.doubleValue();
            double percentage = (value / total) * 100;
            pieChart.getData().add(new PieChart.Data(
                    String.format("%s: %.2f kg CO₂ (%.1f%%)", activityName, value, percentage),
                    value
            ));
        }
    }

    private void refreshTableData() {
        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());
        footprintTableView.getItems().setAll(footprints);
    }

    @Override
    public void onOpen(Object input) throws IOException {}

    public void addHuella() throws Exception {
        App.currentController.changeScene(Scenes.ADDHUELLA, null);
    }

    public void showRecomendations() {
        List<Actividad> activities = activityService.getAllActivities();

        if (activities.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recomendaciones");
            alert.setHeaderText("No tienes actividades registradas");
            alert.setContentText("Realiza alguna actividad para recibir recomendaciones.");
            alert.showAndWait();
            return;
        }
        StringBuilder recommendations = new StringBuilder();
        for (Actividad activity : activities) {

            String activityRecommendations = activityService.getRecommendationByActivity(activity);

            if (activityRecommendations != null && !activityRecommendations.isEmpty()) {
                recommendations.append("Actividad: ").append(activity.getNombre())
                        .append("\n").append(activityRecommendations)
                        .append("\n");
            } else {
                recommendations.append("Actividad: ").append(activity.getNombre())
                        .append("\nNo hay recomendaciones disponibles.")
                        .append("\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recomendaciones");
        alert.setHeaderText("Tus recomendaciones");

        TextArea textArea = new TextArea(recommendations.toString());
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefHeight(200);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    public void addHabit() throws Exception {
        App.currentController.changeScene(Scenes.ADDHABIT, null);
    }

    public void profile() throws Exception {
        App.currentController.changeScene(Scenes.PROFILE, null);
    }

    public void logOut() throws IOException {
        Session.getInstance().logOut();
        App.currentController.changeScene(Scenes.WELCOME, null);
    }


    public void exportToCSV() {
        List<Huella> footprints = footprintService.findByUserID(Session.getInstance().getUserLoged());

        // Crear el archivo CSV
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


    public void exportToPDF() {
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
