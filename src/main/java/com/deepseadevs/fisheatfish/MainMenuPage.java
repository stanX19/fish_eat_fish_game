package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuPage extends BasePage {
    public MainMenuPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label welcomeLabel = new Label("Welcome, " + sessionManager.getUsername() + "!");
        Button logoutButton = new Button("Logout");
        Button startGameButton = new Button("Start Game");

        logoutButton.setOnAction(e -> uiController.logout());
        startGameButton.setOnAction(e -> uiController.gotoGamePage());

        vbox.getChildren().addAll(welcomeLabel, logoutButton, startGameButton);
        return new Scene(vbox, 400, 300);
    }
}
