package com.github.alvader01.View;

import com.github.alvader01.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends Controller implements Initializable {
    @FXML
    Button iniciarSesion;
    @FXML
    Button registrarse;

    public void changeSceneLogin() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
    public void changeSceneRegister() throws IOException {
        App.currentController.changeScene(Scenes.REGISTER, null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }


}
