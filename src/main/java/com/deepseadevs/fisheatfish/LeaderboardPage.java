package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LeaderboardPage extends BasePage {
    public LeaderboardPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        // TODO:
        //  create the leaderboard :)
        //  store the users in LeaderboardUtils
        //  Show the users
        //  Ask gpt if needed

        Label welcomeLabel = new Label("Welcome to the leaderboard page, " +
                                       sessionManager.getUsername() + "!");
        vbox.getChildren().addAll(welcomeLabel);
        return new Scene(vbox, 400, 300);
    }
}

