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
import java.util.ResourceBundle;


public class LoginController extends Controller implements Initializable {

    @FXML
    Button btnLogin;
    @FXML
    Button btnRegister;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField emailField;
    UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Usuario getUser() {
        String email = emailField.getText();
        String password = passwordField.getText();
        return new Usuario(email, password);
    }

    public void login() throws IOException {
        if (usuarioService.loginUser(getUser())) {
            changeSceneToMainPage();
        } else {
            ShowAlertsErrorLogin();
        }
    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }
    public void changeSceneToRegister() throws IOException {
        App.currentController.changeScene(Scenes.REGISTER, null);
    }

    public void ShowAlertsErrorLogin() {
        AppController.ShowAlertsErrorLogin();
    }
}
