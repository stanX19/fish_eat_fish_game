package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuPage implements AppPage{
    private final UIController uiController;
    private final SessionManager sessionManager;
    private Scene scene;

    public MainMenuPage(UIController uiController, SessionManager sessionManager) {
        this.uiController = uiController;
        this.sessionManager = sessionManager;
        createScene();
    }

    private void createScene() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label welcomeLabel = new Label("Welcome, " + sessionManager.getUsername() + "!");
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> uiController.logout());

        vbox.getChildren().addAll(welcomeLabel, logoutButton);
        scene = new Scene(vbox, 400, 300);
    }

    @Override
    public void show(Stage stage) {
        stage.setScene(this.scene);
    }
}
