package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Entities.Usuario;
import com.github.alvader01.Services.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UsuarioService userService = new UsuarioService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic if needed
    }

    @Override
    public void onOpen(Object inputData) throws IOException {
        // Logic for opening the scene, if required
    }

    private Usuario gatherUserData() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        return new Usuario(username, email, password, Instant.now());
    }

    public void handleRegister() throws IOException {
        if (userService.registerUser(gatherUserData())) {
            changeSceneToMainPage();
        } else {
            System.out.println("Registro fallido");
        }
    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }

    public void changeSceneToLoginScreen() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
}
