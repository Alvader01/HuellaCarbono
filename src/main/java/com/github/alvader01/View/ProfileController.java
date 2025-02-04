package com.github.alvader01.View;


import com.github.alvader01.App;
import com.github.alvader01.Connection.Session;
import com.github.alvader01.Entities.Usuario;
import com.github.alvader01.Services.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends Controller implements Initializable {

    @FXML
    private Text welcomeText;

    @FXML
    private Button updateButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    private final UsuarioService userService = new UsuarioService();

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Usuario loggedUser = Session.getInstance().getUserLoged();
        usernameField.setText(loggedUser.getNombre());
        emailField.setText(loggedUser.getEmail());
        passwordField.setText(loggedUser.getContraseña());
        welcomeText.setText("Aqui actualizaras tus datos, " + loggedUser.getNombre() + "!");
    }

    private Usuario collectUserData() throws IOException {
        String name = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Usuario user = new Usuario(name, email, password, Session.getInstance().getUserLoged().getFechaRegistro());
        user.setId(Session.getInstance().getUserLoged().getId());
        return user;
    }

    public void handleUpdate() throws IOException {
        Usuario updatedUser = collectUserData();
        userService.updateUser(updatedUser);
        Session.getInstance().logOut();
        Session.getInstance().logIn(updatedUser);
        changeSceneToMainPage();
    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }
    public void changeSceneToMain() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }
}
