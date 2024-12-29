package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.deepseadevs.fisheatfish.widgets.buttons.ColoredButton;

public class MainMenuPage extends BasePage {
    public MainMenuPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    @Override
    protected Scene createScene() {
        // Create main container with vertical layout and 20 pixels element spacing
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #1a202c;"); // dark background

        // Create title tags
        Label titleLabel = new Label("Fish Eat Fish");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.web("#60a5fa")); // blue title

        // Create welcome tags
        Label welcomeLabel = new Label("Welcome, " + sessionManager.getDisplayName() + "!");
        welcomeLabel.setFont(Font.font("System", 18));
        welcomeLabel.setTextFill(Color.web("#e2e8f0")); // Light gray text

        // Display the highest user score
        Label highScoreLabel = new Label("High Score: " + sessionManager.getHighScore());
        highScoreLabel.setFont(Font.font("System", 16));
        highScoreLabel.setTextFill(Color.web("#fbbf24")); // Gold text

        // Create Key Function
        //Button continueButton = createMainButton("Continue Game", "#22c55e");
        ColoredButton continueButton = new ColoredButton("Continue Game", GameStyles.ACCENT_COLOR);
        continueButton.setOnAction(e -> uiController.gotoGamePage(true));

        //Button startGameButton = createMainButton("New Game", "#22c55e"); // Green color
        ColoredButton startGameButton = new ColoredButton("New Game", GameStyles.ACCENT_COLOR);
        startGameButton.setOnAction(e -> uiController.gotoFishSelectionPage());

        Button leaderboardButton = createMainButton("Leaderboard");
        leaderboardButton.setOnAction(e -> uiController.gotoLeaderBoard());

        //Button selectFishButton = createMainButton("Select Fish");
        //selectFishButton.setOnAction(e -> uiController.gotoFishSelectionPage());

        Button profileButton = createMainButton("Profile");
        profileButton.setOnAction(e -> uiController.gotoHistoryPage());

        // Create utility buttons row
        HBox utilityButtonsRow = new HBox(20);
        utilityButtonsRow.setAlignment(Pos.CENTER);

        /*Button settingsButton = createUtilityButton("Settings", "#64748b"); // Gray-blue color
        settingsButton.setOnAction(e -> handleSettings());

        Button helpButton = createUtilityButton("Help", "#64748b"); // Gray-blue color
        helpButton.setOnAction(e -> handleHelp());

        utilityButtonsRow.getChildren().addAll(settingsButton, helpButton);*/

        // Create logout button
        //Button logoutButton = createMainButton("Logout", "#ef4444"); // Red color
        ColoredButton logoutButton = new ColoredButton("Logout", GameStyles.SECONDARY_COLOR);
        logoutButton.setOnAction(e -> uiController.logout());

        // Add all elements to the main container
        root.getChildren().addAll(
                titleLabel,
                welcomeLabel,
                highScoreLabel
        );

        // Add Continue Game button if there's an ongoing game
        if (sessionManager.hasOngoingGame()) {
            root.getChildren().addAll(continueButton);
        }

        root.getChildren().addAll(
                startGameButton,
                leaderboardButton,
                //selectFishButton,
                profileButton,
                utilityButtonsRow,
                logoutButton
        );

        // Create the scene and return
        return new Scene(root, 600, 800);
    }

    private Button createMainButton(String text) {
        return createMainButton(text, "#3b82f6"); // Default blue color
    }

    private Button createMainButton(String text, String color) {
        Button button = new Button(text);
        String buttonStyle = String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-min-width: 300px;
                """, color);

        String buttonHoverStyle = String.format("""
                -fx-background-color: derive(%s, -20%%);
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-min-width: 300px;
                """, color);

        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> button.setStyle(buttonHoverStyle));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));

        return button;
    }

    private Button createUtilityButton(String text, String color) {
        Button button = new Button(text);
        String buttonStyle = String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-min-width: 140px;
                """, color);

        String buttonHoverStyle = String.format("""
                -fx-background-color: derive(%s, -20%%);
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-min-width: 140px;
                """, color);

        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> button.setStyle(buttonHoverStyle));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));

        return button;
    }

    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Set the style of the dialog box,dark background,white text
        alert.getDialogPane().setStyle("""
            -fx-background-color: #1a202c;
            """);

        alert.getDialogPane().lookup(".content.label").setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-font-family: System;
            """);

        alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setStyle(""" 
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8px 16px;
            -fx-background-radius: 4px;
            """);

        alert.showAndWait();
    }

    private void handleSettings() {
        String settingsInfo = """
            Game Controls:
            - WASD or Arrow Keys to move
            - Eat smaller fish to grow
            - Avoid larger fish

            Additional settings coming soon!
            """;

        showAlert("Game Settings", settingsInfo, AlertType.INFORMATION);
    }

    private void handleHelp() {
        String helpInfo = """
            How to Play:
            1. Use WASD or Arrow Keys to navigate
            2. Eat fish smaller than you to grow
            3. Avoid larger fish - they will eat you!
            4. The larger you get, the more points you earn
            5. Try to achieve the highest score possible!

            Tips:
            - Start with the smallest fish
            - Watch out for fast-moving large fish
            - Keep track of your size relative to others
            - Practice makes perfect!
            """;

        showAlert("Game Help", helpInfo, AlertType.INFORMATION);
    }
}
