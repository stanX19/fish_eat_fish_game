package com.deepseadevs.fisheatfish;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private UIController uiController;

    @Override
    public void start(Stage primaryStage) {
        uiController = new UIController(primaryStage);
        uiController.gotoLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
