package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.widgets.GameStyles;
import com.deepseadevs.fisheatfish.widgets.labels.SubScriptLabel;
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

import java.util.Random;

public class MainMenuPage extends BasePage {
    Random random;
    int tipsIdx;

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

        // Display tips
        String[] tipsArr = {
                "Stay away from larger fishes!",
                "Level can reflect your fish power",
                "Beware! Large fish may emerge from screen edges",
                "Keep your distance from the edges!",
                "WASD to move your fish",
                "Check the Leaderboard to compare your strength",
                "Player's name in leaderboard is clickable",
                "Click profile to view your progress",
                "Eat smaller fish, avoid larger fish",
                "Hunt larger fish to grow faster!",
                "Higher score unlock more levels",
                "Reach higher level to unlock fishes!",
                "Don't share your account, you may lose it forever",
                "You get extra scores for every fish eaten",
                "Larger fish appear small, watch out!",
                "Try to eat bigger fishes - maybe they aren't!"
        };
        random = new Random();
        tipsIdx = random.nextInt(tipsArr.length);
        Label tipsLabel = new SubScriptLabel("Tips: " + tipsArr[tipsIdx]);
        tipsLabel.setOnMouseClicked(e -> {
            tipsIdx += 1 + random.nextInt(tipsArr.length - 2);
            tipsIdx %= tipsArr.length;
            tipsLabel.setText("Tips: " + tipsArr[tipsIdx]);
        });

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

        // profile button
        Button profileButton = new MenuColoredButton("Profile", GameStyles.MAIN_COLOR);
        profileButton.setOnAction(e -> uiController.gotoHistoryPage());

        // Create logout button
        MenuColoredButton logoutButton = new MenuColoredButton("Logout", GameStyles.ACCENT_COLOR);
        logoutButton.setOnAction(e -> uiController.logout());
        logoutButton.setStyle(logoutButton.getStyle() + "-fx-min-width: 300px;");

        // Add all elements to the main container
        root.getChildren().addAll(
                titleLabel,
                welcomeLabel,
                highScoreLabel,
                tipsLabel
        );

        if (sessionManager.hasOngoingGame()) {
            root.getChildren().add(continueButton);
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
