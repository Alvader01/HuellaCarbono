package com.github.alvader01.View;

import com.github.alvader01.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @FXML
    static Alert alert= new Alert(Alert.AlertType.ERROR);

    @FXML
    static Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    static Alert alertInfoRegister= new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.WELCOME, null);
    }


    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }

    public void changeScene(Scenes scene, Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scenes, String tilte, Controller parent, Object data) throws Exception {
        View view = loadFXML(scenes);
        Stage stage = new Stage();
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setTitle(tilte);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(data);
        stage.showAndWait();
    }
    public static void ShowAlertsErrorLogin(){
        alert.setTitle("Error");
        alert.setContentText("Email o contraseña incorrectos");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLoginPassword(){
        alert.setTitle("Error");
        alert.setContentText("La contraseña es incorrecta");
        alert.showAndWait();
    }

    public static void ShowAlertsErrorRegister(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el usuario");
        alert.showAndWait();
    }

    public static void ShowAlertsEmailExists(){
        alert.setTitle("Error");
        alert.setContentText("El email ya existe");
        alert.showAndWait();
    }

    public static void  ShowInformationValue(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Las unidades de medida son Km para transporte, KWh para energia " +
                ",Kg para alimentacion y residuos y m³ para agua respectivamente");
        alertInfoRegister.showAndWait();
    }
    public static void ShowErrorValue(){
        alert.setTitle("Error");
        alert.setContentText("El valor seleccionado no es valido");
        alert.showAndWait();
    }
    public static void showErrorActivityNotSelected(){
        alert.setTitle("Error");
        alert.setContentText("No hay ninguna actividad seleccionada");
        alert.showAndWait();
    }
    public static void showFootprintSaved (){
        alert2.setTitle("Exito");
        alert2.setContentText("La huella ha sido guardada con exito");
        alert2.showAndWait();
    }
    public static void showErrorFootprintNotSaved(){
        alert.setTitle("Error");
        alert.setContentText("La huella no se ha podido guardar");
        alert.showAndWait();
    }
    public static void showErrorFrecuency(){
        alert.setTitle("Error");
        alert.setContentText("La frecuencia debe ser mayor a 0");
        alert.showAndWait();
    }
    public static void showSavedHabit (){
        alert2.setTitle("Exito");
        alert2.setContentText("El habito ha sido guardado con exito");
        alert2.showAndWait();
    }
    public static void showErrorHabitExists(){
        alert.setTitle("Error");
        alert.setContentText("El habito ya existe");
        alert.showAndWait();
    }
    public static void showErrorFootprintExists() {
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().setPrefWidth(300);
        alert.setTitle("Huella Existente");
        alert.setHeaderText("Esta huella ya existe");
        alert.setContentText("Ya tienes una huella registrada para esta actividad en la misma fecha.");
        alert.showAndWait();
    }
    public static void showErrorNoRecomendations() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recomendaciones");
        alert.setHeaderText("No tienes huellas registradas");
        alert.setContentText("Agrega alguna huella de carbono para recibir recomendaciones.");
        alert.showAndWait();

    }












}
