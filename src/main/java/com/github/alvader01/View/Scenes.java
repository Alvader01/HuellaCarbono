package com.github.alvader01.View;

public enum Scenes {
    ROOT("view/layout.fxml"),
    LOGIN("view/login.fxml"),
    REGISTER("view/register.fxml"),
    MAINPAGE("view/mainpage.fxml"),
    ADDHUELLA("view/addHuella.fxml"),
    ADDHABIT("view/addHabito.fxml"),
    RECOMENDATIONS("view/recomendations.fxml"),
    PROFILE("view/profile.fxml"),
    WELCOME("view/welcome.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
