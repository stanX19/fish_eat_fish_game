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

        // TODO:
        //  add appropriate buttons
        //  add good design to buttons
        Label welcomeLabel = new Label("Welcome, " + sessionManager.getUsername() + "!");
        Button logoutButton = new Button("Logout");
        Button startGameButton = new Button("Start Game");
        Button leaderboardButton = new Button("Leaderboard");

        logoutButton.setOnAction(e -> uiController.logout());
        startGameButton.setOnAction(e -> uiController.gotoGamePage());
        leaderboardButton.setOnAction(e -> uiController.gotoLeaderBoard());

        vbox.getChildren().addAll(welcomeLabel, logoutButton, startGameButton, leaderboardButton);
        return new Scene(vbox, 400, 300);
    }
}
