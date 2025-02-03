package com.github.alvader01;

import com.github.alvader01.View.AppController;
import com.github.alvader01.View.Scenes;
import com.github.alvader01.View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    @Override
    public void start(Stage stage) throws IOException {
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 1280, 720);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}