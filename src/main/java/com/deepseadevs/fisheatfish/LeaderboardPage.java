package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
import com.deepseadevs.fisheatfish.widgets.labels.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class LeaderboardPage extends BasePage {
    private final String BACKGROUND_COLOR = "#1a202c";

    public LeaderboardPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        // Centered VBox for content
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(20);
        contentBox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Title label
        TitleLabel titleLabel = new TitleLabel("LEADERBOARD");

        // Back button
        MainButton backButton = new MainButton("Back");
        backButton.setOnAction(e -> uiController.gotoPreviousPage());
        backButton.setFocusTraversable(false);

        // Leaderboard layout
        VBox leaderboardLayout = createLeaderboardLayout();

        // Add content to the box
        contentBox.getChildren().addAll(titleLabel, leaderboardLayout, backButton);

        // Wrap contentBox in a ScrollPane to allow scrolling for the entire scene
        ScrollPane root = new ScrollPane(contentBox);
        root.setFitToWidth(true);
        root.setFitToHeight(true);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        root.setPadding(new Insets(20));

        return new Scene(root, 700, 500); // Adjust dimensions as necessary
    }

    private VBox createLeaderboardLayout() {
        VBox leaderboardLayout = new VBox();
        leaderboardLayout.setSpacing(10); // Space between rows
        leaderboardLayout.setPadding(new Insets(15));
        leaderboardLayout.setStyle("-fx-background-radius: 10; -fx-background-color: derive(" + BACKGROUND_COLOR + ", 10%);");
        leaderboardLayout.setMaxWidth(500);
        leaderboardLayout.setAlignment(Pos.CENTER);

        // Header row
        HBox headerRow = createRow("Rank", "Username", "High Score", "", 0);
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

            HBox row = createRow(String.valueOf(rank), user.getDisplayName(), String.valueOf(user.getHighScore()), user.getUserID(), isCurrentUser ? 2 : 1);
            leaderboardLayout.getChildren().add(row);
            rank++;
        }

        if (!isUserOnLeaderboard) {
            UserData currentUser = DatabaseManager.getInstance().getUserData(sessionManager.getUserID());
            HBox currentUserRow = createRow("-", currentUser.getDisplayName(), String.valueOf(currentUser.getHighScore()), currentUser.getUserID(), 2);
            leaderboardLayout.getChildren().add(currentUserRow);
        }

        return leaderboardLayout;
    }

    // type: 0 => header, 1 => normal text, 2 => colored text
    private HBox createRow(String rank, String username, String score, String userID, int type) {
        final String[] text = { rank, username, score, userID };
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(10));

        // Create labels for each column
        Label[] labels = new Label[3];
        switch (type) {
            case 0 -> {

                for (int i = 0; i < 3; i++) {
                    labels[i] = new BoldLabel(text[i]);
                    setLabelProperties(labels[i]);
                }
            }
            case 1 -> {
                for (int i = 0; i < 3; i++) {
                    labels[i] = new NeutralLabel(text[i]);
                    setLabelProperties(labels[i]);
                }
            }
            case 2 -> {
                for (int i = 0; i < 3; i++) {
                    labels[i] = new ColoredLabel(text[i], "#d3d3ff");
                    setLabelProperties(labels[i]);
                }
            }
        }

        if (type != 0) {
            String borderColor = rank.equals("1") ? "#efbf04" : rank.equals("2") ? "#c4c4c4": rank.equals("3") ? "#ce8946" : "null";
            final String ROW_STYLE = String.format("""
                    -fx-background-color: rgba(255, 255, 255, 0.1);
                    -fx-background-radius: 5px;
                    -fx-border-color: %s;
                    """, borderColor);
            final String ROW_HOVER_STYLE = String.format("""
                    -fx-background-color: derive(rgba(255, 255, 255, 0.1), -20%%);
                    -fx-background-radius: 5px;
                    -fx-border-color: %s;
                    -fx-cursor: hand;
                    """, borderColor);
            row.setStyle(ROW_STYLE);
            row.setOnMouseClicked(e -> {
                uiController.gotoHistoryPage(DatabaseManager.getInstance().getUserData(userID));
            });
            row.setOnMouseEntered(e -> {
                row.setStyle(ROW_HOVER_STYLE);
            });
            row.setOnMouseExited(e -> {
                row.setStyle(ROW_STYLE);
            });

            VBox vbox = new VBox();
            Label userIDLabel = new GeneralLabel(userID, Font.font("Arial", 10), "#888888");
            setLabelProperties(userIDLabel);
            vbox.getChildren().addAll(labels[1], userIDLabel);
            row.getChildren().addAll(labels[0], vbox, labels[2]);
            return row;
        }

        // Add labels to the row
        row.getChildren().addAll(labels);

        return row;
    }

    private void setLabelProperties(Label label) {
        final double COLUMN_WIDTH = 150;
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMinWidth(COLUMN_WIDTH);
        label.setMaxWidth(Double.MAX_VALUE);
    }

}
