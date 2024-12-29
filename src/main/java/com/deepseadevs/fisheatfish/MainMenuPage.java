package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.widgets.GameStyles;
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
import com.deepseadevs.fisheatfish.widgets.buttons.MenuColoredButton;

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
        MenuColoredButton continueButton = new MenuColoredButton("Continue Game", GameStyles.SECONDARY_COLOR);
        continueButton.setOnAction(e -> uiController.gotoGamePage(true));
        continueButton.setStyle(continueButton.getStyle() + "-fx-min-width: 300px;");

        //Button startGameButton = createMainButton("New Game", "#22c55e"); // Green color
        MenuColoredButton startGameButton = new MenuColoredButton("New Game", GameStyles.SECONDARY_COLOR);
        startGameButton.setOnAction(e -> uiController.gotoFishSelectionPage());
        startGameButton.setStyle(startGameButton.getStyle() + "-fx-min-width: 300px;");

        Button leaderboardButton = new MenuColoredButton("Leaderboard", GameStyles.MAIN_COLOR);
        leaderboardButton.setOnAction(e -> uiController.gotoLeaderBoard());

        //Button selectFishButton = createMainButton("Select Fish");
        //selectFishButton.setOnAction(e -> uiController.gotoFishSelectionPage());

        Button profileButton = new MenuColoredButton("Profile", GameStyles.MAIN_COLOR);
        profileButton.setOnAction(e -> uiController.gotoHistoryPage());

        /*
        // Create utility buttons row
        HBox utilityButtonsRow = new HBox(20);
        utilityButtonsRow.setAlignment(Pos.CENTER);

        Button settingsButton = createUtilityButton("Settings", "#64748b"); // Gray-blue color
        settingsButton.setOnAction(e -> handleSettings());

        Button helpButton = createUtilityButton("Help", "#64748b"); // Gray-blue color
        helpButton.setOnAction(e -> handleHelp());

        utilityButtonsRow.getChildren().addAll(settingsButton, helpButton);*/

        // Create logout button
        //Button logoutButton = createMainButton("Logout", "#ef4444"); // Red color
        MenuColoredButton logoutButton = new MenuColoredButton("Logout", GameStyles.ACCENT_COLOR);
        logoutButton.setOnAction(e -> uiController.logout());
        logoutButton.setStyle(logoutButton.getStyle() + "-fx-min-width: 300px;");

        // Add all elements to the main container
        root.getChildren().addAll(
                titleLabel,
                welcomeLabel,
                highScoreLabel
        );

        if (sessionManager.hasOngoingGame()) {
            root.getChildren().addAll(continueButton);
        }

        root.getChildren().addAll(
                startGameButton,
                leaderboardButton,
                profileButton,
                logoutButton
        );

        return new Scene(root, 600, 800);
    }
}
