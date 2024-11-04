package com.deepseadevs.fisheatfish;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private Controller controller;
    private DataHandler dataHandler;
    private CurrentPlayer currentPlayer;

    @Override
    public void start(Stage primaryStage) {
        dataHandler = new DataHandler();
        currentPlayer = new CurrentPlayer();
        controller = new Controller(primaryStage, this);

        controller.showLoginPage();
    }

    public Controller getController() {
        return controller;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public CurrentPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

