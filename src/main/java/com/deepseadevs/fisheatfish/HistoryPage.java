package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.*;

public class HistoryPage extends BasePage {
    private List<GameData> history;

    public HistoryPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
        this.history = sessionManager.getGameHistory();
    }

    public HistoryPage(UIController uiController, SessionManager sessionManager, UserData userData) {
        super(uiController, sessionManager);
        this.history = userData.getHistory();
    }

    protected Scene createScene() {
        // Centered VBox for content
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(20);
        contentBox.setStyle("-fx-background-color: #1a2b40;");

        // Welcome label
        Label welcomeLabel = new Label("HISTORY");
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
        VBox historyLayout = createHistoryLayout();

        // Add content to the box
        contentBox.getChildren().addAll(welcomeLabel, historyLayout, backButton);

        // Wrap contentBox in a ScrollPane to allow scrolling for the entire scene
        ScrollPane root = new ScrollPane(contentBox);
        root.setFitToWidth(true); // Make the scene's content stretch horizontally
        root.setFitToHeight(true);
        root.setStyle("-fx-background-color: #1a2b40;");
        root.setPadding(new Insets(20));

        return new Scene(root, 800, 500); // Adjust dimensions as necessary
    }

    private VBox createHistoryLayout() {
//        List<GameData> history = sessionManager.getGameHistory();

        VBox historyLayout = new VBox();
        historyLayout.setSpacing(10); // Space between rows
        historyLayout.setPadding(new Insets(15));
        historyLayout.setStyle("-fx-background-radius: 10; -fx-background-color: #26364e;");
        historyLayout.setMaxWidth(500);
        historyLayout.setAlignment(Pos.CENTER);

        // Header row
        HBox headerRow = createRow("Date", "Level", "Score", "Duration", true);
        historyLayout.getChildren().add(headerRow);

        // Create rows
        history.sort(Comparator.comparing(GameData::getEndTime).reversed());
        for (GameData data : history) {
            String date = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(Date.from(data.getEndTime()));
            long s = data.getGameDuration().getSeconds();
            String duration = String.format("%d:%02d", s/60, s%60);
            HBox row = createRow(date, String.valueOf(data.getLevel()), String.valueOf(data.getScore()), duration, false);
            historyLayout.getChildren().add(row);
        }

        return historyLayout;
    }

    private HBox createRow(String date, String level, String score, String duration, boolean isHeader) {
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(5));

        // Apply background styles
        if (isHeader)
            row.setStyle("-fx-padding: 10px;");
        else
            row.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-padding: 10px; -fx-background-radius: 5px;");

        // Create labels for each column
        Label dateLabel = new Label(date);
        Label levelLabel = new Label(level);
        Label scoreLabel = new Label(score);
        Label durationLabel = new Label(duration);


        styleCell(dateLabel, isHeader);
        styleCell(levelLabel, isHeader);
        styleCell(scoreLabel, isHeader);
        styleCell(durationLabel, isHeader);

        // Ensure the labels are properly spaced
        HBox.setHgrow(dateLabel, Priority.ALWAYS);
        HBox.setHgrow(levelLabel, Priority.ALWAYS);
        HBox.setHgrow(scoreLabel, Priority.ALWAYS);
        HBox.setHgrow(durationLabel, Priority.ALWAYS);

        double columnWidth = 150; // Adjust as needed
        dateLabel.setMinWidth(columnWidth);
        levelLabel.setMinWidth(columnWidth);
        scoreLabel.setMinWidth(columnWidth);
        durationLabel.setMinWidth(columnWidth);

        dateLabel.setMaxWidth(Double.MAX_VALUE);
        levelLabel.setMaxWidth(Double.MAX_VALUE);
        scoreLabel.setMaxWidth(Double.MAX_VALUE);
        durationLabel.setMaxWidth(Double.MAX_VALUE);

        // Add labels to the row
        row.getChildren().addAll(dateLabel, levelLabel, scoreLabel, durationLabel);

        return row;
    }

    private void styleCell(Label label, boolean isHeader) {
        String fontWeight = isHeader ? "bold" : "normal";

        label.setStyle("-fx-font-size: 14px; -fx-font-weight: " + fontWeight + "; -fx-text-fill: #ffffff; -fx-alignment: center;");
    }

}
