package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.database.DatabaseManager;
import com.deepseadevs.fisheatfish.database.SessionManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private UIController uiController;
    private SessionManager sessionManager;

    @Override
    public void start(Stage primaryStage) {
        sessionManager = new SessionManager();
        DatabaseManager.getInstance();
        uiController = new UIController(primaryStage, sessionManager);
        uiController.gotoLogin();
        primaryStage.setTitle("Fish Eat Fish");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
