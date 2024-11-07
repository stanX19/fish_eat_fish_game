package com.deepseadevs.fisheatfish;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private UIController uiController;
    private SessionManager sessionManager;

    @Override
    public void start(Stage primaryStage) {
        sessionManager = new SessionManager();
        uiController = new UIController(primaryStage, sessionManager);
        uiController.gotoLogin();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
