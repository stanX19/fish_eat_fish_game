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

// TODO:
//  if sessionManager.hasOngoingGame()
//   Add continue button on menu page, to continue previous game
//   using uiController.gotoGamePage(true);
//  Add history button to go to history page
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
        Button startGameButton = createMainButton("Start Game", "#22c55e"); // Green start button
        startGameButton.setOnAction(e -> uiController.gotoGamePage());

        Button leaderboardButton = createMainButton("Leaderboard", "#3b82f6"); // Blue leaderboard button
        leaderboardButton.setOnAction(e -> handleLeaderboard());

        Button profileButton = createMainButton("Profile", "#9333ea"); // Purple profile button
        profileButton.setOnAction(e -> handleProfile());

        // Create tool buttons
        HBox utilityButtonsRow = new HBox(20);
        utilityButtonsRow.setAlignment(Pos.CENTER);

        Button settingsButton = createUtilityButton("Settings");
        settingsButton.setOnAction(e -> handleSettings());

        Button helpButton = createUtilityButton("Help");
        helpButton.setOnAction(e -> handleHelp());

        utilityButtonsRow.getChildren().addAll(settingsButton, helpButton);

        // Create a logout button
        Button logoutButton = createMainButton("Logout", "#dc2626"); // Red logout button
        logoutButton.setOnAction(e -> uiController.logout());

        // add all elements to the main container
        root.getChildren().addAll(
                titleLabel,
                welcomeLabel,
                highScoreLabel,
                startGameButton,
                leaderboardButton,
                profileButton,
                utilityButtonsRow,
                logoutButton
        );

        // Create the scene and return
        return new Scene(root, 600, 800);
    }

    // by button style - gray background
    private static final String BUTTON_STYLE = """
            -fx-background-color: #4a5568;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-padding: 12px 24px;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
            """;

    // Button hover style - Dark gray background
    private static final String BUTTON_HOVER_STYLE = """
            -fx-background-color: #2d3748;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-padding: 12px 24px;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
            """;

    /**
     * createMainButton
     */
    private Button createMainButton(String text, String color) {
        Button button = new Button(text);
        // Set the basic style of buttons
        button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-min-width: 300px;
                -fx-cursor: hand;
                """, color));

        // Set the mouse hover effect
        button.setOnMouseEntered(e -> button.setStyle(String.format("""
                -fx-background-color: derive(%s, -20%%);
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-min-width: 300px;
                -fx-cursor: hand;
                """, color)));

        //Resort original style
        button.setOnMouseExited(e -> button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-min-width: 300px;
                -fx-cursor: hand;
                """, color)));

        return button;
    }

    /**
     * createUtilityButton
     */
    private Button createUtilityButton(String text) {
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.setMinWidth(140);

        button.setOnMouseEntered(e -> button.setStyle(BUTTON_HOVER_STYLE + "-fx-min-width: 140px;"));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_STYLE + "-fx-min-width: 140px;"));

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

        // Get the content area and set the text to white
        alert.getDialogPane().lookup(".content.label").setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-font-family: System;
            """);

        // Set the button style
        // changed getFirst to get(0)
        alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setStyle(""" 
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8px 16px;
            -fx-background-radius: 4px;
            """);

        alert.showAndWait();
    }

    /**
     * Handle leaderboard button click events
     * Display the current user's highest score
     */
    private void handleLeaderboard() {
        uiController.gotoLeaderBoard();
//        String leaderboardInfo = String.format("""
//            🏆 Current High Scores 🏆
//            ═══════════════════
//
//            👤 Player: %s
//            ⭐ High Score: %d
//
//            ───────────────────
//
//            💡 Note:
//            Full leaderboard coming soon!
//            Keep improving your score
//            to achieve a higher rank!
//            """,
//                sessionManager.getDisplayName(),
//                sessionManager.getHighScore()
//        );
//
//        showAlert("The charts", leaderboardInfo, AlertType.INFORMATION);
    }

    /**
     * Handle profile button click events
     * Display user information and account management options
     */
    private void handleProfile() {
        String profileInfo = String.format("""
            Username: %s
            High Score: %d
            
            To delete your account or change password,
            please contact an administrator.
            """,
                sessionManager.getDisplayName(),
                sessionManager.getHighScore()
        );

        showAlert("Profile Information", profileInfo, AlertType.INFORMATION);
    }

    /**
     * Handle settings button click events
     * Display game control descriptions and setup options
     */
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

    /**
     * Handle help button click events
     * Show game play instructions tips
     */
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
