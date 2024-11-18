package com.deepseadevs.fisheatfish;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class LeaderboardPage extends BasePage {

    public LeaderboardPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        // TODO:
        //  change the colors
        //  change the appearance of the grid such that each column is not detached
        //  add small arrow beside player name if player is on leaderboard
        //  if player is not on leaderboard show player name and score at the bottom
        //  of the leaderboard

        // Root container with gradient background
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");
        root.setPadding(new Insets(20));

        // Centered VBox for content
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(20);

        // Welcome label
        Label welcomeLabel = new Label("Welcome, " + sessionManager.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> uiController.gotoMainMenu());
        backButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333; -fx-font-size: 14px; -fx-padding: 5 10; "
                + "-fx-border-color: #666; -fx-border-width: 1px; -fx-border-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #ffcc00; -fx-text-fill: #000; "
                + "-fx-font-size: 14px; -fx-padding: 5 10; -fx-border-color: #333; -fx-border-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333; "
                + "-fx-font-size: 14px; -fx-padding: 5 10; -fx-border-color: #666; -fx-border-radius: 5px;"));

        // Leaderboard grid
        GridPane leaderboardGrid = createLeaderboardGrid();

        // Add content to the box
        contentBox.getChildren().addAll(welcomeLabel, leaderboardGrid, backButton);

        // Center content in the window
        root.getChildren().add(contentBox);

        return new Scene(root, 700, 500); // Adjust dimensions as necessary
    }

    private GridPane createLeaderboardGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 10; -fx-background-radius: 10;");
        grid.setMaxWidth(500);
        grid.setAlignment(Pos.CENTER);

        // Header row with colored background
        Label rankHeader = new Label("Rank");
        Label usernameHeader = new Label("Username");
        Label scoreHeader = new Label("High Score");
        styleHeader(rankHeader);
        styleHeader(usernameHeader);
        styleHeader(scoreHeader);

        grid.add(rankHeader, 0, 0);
        grid.add(usernameHeader, 1, 0);
        grid.add(scoreHeader, 2, 0);

        GridPane.setHalignment(rankHeader, HPos.CENTER);
        GridPane.setHalignment(usernameHeader, HPos.CENTER);
        GridPane.setHalignment(scoreHeader, HPos.CENTER);

        // Fetch and sort top players
        Collection<UserData> topPlayersRaw = LeaderboardUtils.getTopUsers(10);
        List<UserData> topPlayers = new ArrayList<>(topPlayersRaw);
        topPlayers.sort(Comparator.comparingLong((UserData u) -> u.highScore).reversed());

        if (topPlayers.isEmpty()) {
            Label noDataLabel = new Label("No players on the leaderboard yet.");
            noDataLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            grid.add(noDataLabel, 0, 1, 3, 1); // Spans all columns
            GridPane.setHalignment(noDataLabel, HPos.CENTER);
        } else {
            int row = 1;
            int rank = 1;
            for (UserData user : topPlayers) {
                Label rankLabel = new Label(String.valueOf(rank));
                Label usernameLabel = new Label(user.username);
                Label scoreLabel = new Label(String.valueOf(user.highScore));

                boolean isCurrentUser = user.username.equals(sessionManager.getUsername());
                styleCell(rankLabel, row, isCurrentUser);
                styleCell(usernameLabel, row, isCurrentUser);
                styleCell(scoreLabel, row, isCurrentUser);

                grid.add(rankLabel, 0, row);
                grid.add(usernameLabel, 1, row);
                grid.add(scoreLabel, 2, row);

                GridPane.setHalignment(rankLabel, HPos.CENTER);
                GridPane.setHalignment(scoreLabel, HPos.CENTER);

                row++;
                rank++;
            }
        }

        return grid;
    }

    private void styleHeader(Label label) {
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-background-color: #007bff; "
                + "-fx-padding: 10; -fx-border-color: #333; -fx-border-radius: 5px; -fx-border-width: 1;");
    }

    private void styleCell(Label label, int row, boolean highlight) {
        String backgroundColor = highlight ? "#ffcc00" : (row % 2 == 0 ? "#f9f9f9" : "#e0e0e0");
        String textColor = highlight ? "#000000" : "#333333";
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: " + textColor + "; -fx-background-color: " + backgroundColor + "; "
                + "-fx-padding: 5px; -fx-border-color: #ddd; -fx-border-width: 1px;");
    }
}
