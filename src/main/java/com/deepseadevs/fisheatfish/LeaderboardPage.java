package com.deepseadevs.fisheatfish;

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
        //  if player is not on leaderboard show player name and score at the bottom
        //  of the leaderboard
        //  show very small, not obvious userid text below user's name

        // Root container with gradient background
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a2b40, #162733);");
        root.setPadding(new Insets(20));

        // Centered VBox for content
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(20);

        // Welcome label
        Label welcomeLabel = new Label("LEADERBOARD");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #b5c7eb;");

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> uiController.gotoMainMenu());
        final String BUTTON_STYLE = """
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-padding: 12px 24px;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
            """;
        final String BUTTON_HOVER_STYLE = """
            -fx-background-color: derive(#3b82f6, -20%);
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-padding: 12px 24px;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
            """;
        backButton.setStyle(BUTTON_STYLE);
        backButton.setOnMouseEntered(e -> backButton.setStyle(BUTTON_HOVER_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(BUTTON_STYLE));

        // Leaderboard layout
        VBox leaderboardLayout = createLeaderboardLayout();

        // Add content to the box
        contentBox.getChildren().addAll(welcomeLabel, leaderboardLayout, backButton);

        // Center content in the window
        root.getChildren().add(contentBox);

        return new Scene(root, 1000, 700); // Adjust dimensions as necessary
    }

    private VBox createLeaderboardLayout() {
        VBox leaderboardLayout = new VBox();
        leaderboardLayout.setSpacing(10); // Space between rows
        leaderboardLayout.setPadding(new Insets(15));
//        leaderboardLayout.setStyle("-fx-background-radius: 10; -fx-background-color: #26364e;");
        leaderboardLayout.setMaxWidth(500);
        leaderboardLayout.setAlignment(Pos.CENTER);

        // Header row
        HBox headerRow = createRow("Rank", "Username", "High Score", "", true, false);
        leaderboardLayout.getChildren().add(headerRow);

        // Fetch and sort top players
        Collection<UserData> topPlayersRaw = LeaderboardUtils.getTopUsers(10);
        List<UserData> topPlayers = new ArrayList<>(topPlayersRaw);
        topPlayers.sort(Comparator.comparingLong(UserData::getHighScore).reversed());

        boolean isUserOnLeaderboard = false;

        int rank = 1;
        for (UserData user : topPlayers) {
            boolean isCurrentUser = user.getUserID().equals(sessionManager.getUserID());
            if (isCurrentUser) {
                isUserOnLeaderboard = true;
            }

            HBox row = createRow(String.valueOf(rank), user.getDisplayName(), String.valueOf(user.getHighScore()), user.getUserID(),false, isCurrentUser);
            leaderboardLayout.getChildren().add(row);
            rank++;
        }

        if (!isUserOnLeaderboard) {
            UserData currentUser = DatabaseManager.getInstance().getUserData(sessionManager.getUserID());
            HBox currentUserRow = createRow("N/A", currentUser.getDisplayName(), String.valueOf(currentUser.getHighScore()), currentUser.getUserID(), false, true);
            leaderboardLayout.getChildren().add(currentUserRow);
        }

        return leaderboardLayout;
    }

    private HBox createRow(String rank, String username, String score, String userID, boolean isHeader, boolean isHighlighted) {
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(5));

        // Apply background styles
        if (isHeader) {
            row.setStyle("-fx-padding: 10px;");
        } else {
            String borderColor = rank.equals("1") ? "#efbf04" : rank.equals("2") ? "#c4c4c4": rank.equals("3") ? "#ce8946" : "null";
            row.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-color: " + borderColor + ";");
        }

        // Create labels for each column
        Label rankLabel = new Label(rank);
        Label usernameLabel = new Label(username);
        Label scoreLabel = new Label(score);

        styleCell(rankLabel, isHeader, isHighlighted, false);
        styleCell(usernameLabel, isHeader, isHighlighted, false);
        styleCell(scoreLabel, isHeader, isHighlighted, false);

        // Ensure the labels are properly spaced
        HBox.setHgrow(rankLabel, Priority.ALWAYS);
        HBox.setHgrow(usernameLabel, Priority.ALWAYS);
        HBox.setHgrow(scoreLabel, Priority.ALWAYS);

        double columnWidth = 150; // Adjust as needed
        rankLabel.setMinWidth(columnWidth);
        usernameLabel.setMinWidth(columnWidth);
        scoreLabel.setMinWidth(columnWidth);

        rankLabel.setMaxWidth(Double.MAX_VALUE);
        usernameLabel.setMaxWidth(Double.MAX_VALUE);
        scoreLabel.setMaxWidth(Double.MAX_VALUE);

        if (!userID.isEmpty()) {
            VBox vbox = new VBox();
            Label userIDLabel = new Label(userID);
            styleCell(userIDLabel, isHeader, isHighlighted, true);
            HBox.setHgrow(userIDLabel, Priority.ALWAYS);
            userIDLabel.setMinWidth(columnWidth);
            userIDLabel.setMaxWidth(Double.MAX_VALUE);
            vbox.getChildren().addAll(usernameLabel, userIDLabel);
            row.getChildren().addAll(rankLabel, vbox, scoreLabel);
            return row;
        }

        // Add labels to the row
        row.getChildren().addAll(rankLabel, usernameLabel, scoreLabel);

        return row;
    }

    private void styleCell(Label label, boolean isHeader, boolean isHighlighted, boolean isUserID) {
        String fontSize = isHeader ? "16px" : isUserID ? "10px" : "14px";
        String fontWeight = isHeader ? "bold" : "normal";
        String textColor = isUserID ? "#898989" : isHighlighted ? "#adebb3" : "#ffffff";

        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + fontWeight + "; -fx-text-fill: " + textColor + "; -fx-alignment: center;");
    }
}
