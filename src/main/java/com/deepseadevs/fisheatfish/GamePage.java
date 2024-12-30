package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;
import com.deepseadevs.fisheatfish.game.GameEngine;
import com.deepseadevs.fisheatfish.game.Settings;
import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
import com.deepseadevs.fisheatfish.widgets.buttons.MenuMainButton;
import com.deepseadevs.fisheatfish.widgets.buttons.MenuSecondaryButton;
import com.deepseadevs.fisheatfish.widgets.labels.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.Duration;

public class GamePage extends BasePage {
    private Canvas canvas;
    private GameData gameData;
    private GameEngine gameEngine;
    private GameOverOverlay gameOverOverlay;
    private Pane pauseOverlay;
    Button continueButton;

    public GamePage(UIController uiController, SessionManager sessionManager) {
        this(uiController, sessionManager, false);
    }

    public GamePage(UIController uiController, SessionManager sessionManager, boolean continueGame) {
        this(uiController, sessionManager, continueGame ? sessionManager.getPreviousGameData() : sessionManager.createNewGameData());
    }

    public GamePage(UIController uiController, SessionManager sessionManager, GameData gameData) {
        super(uiController, sessionManager);
        this.gameData = gameData;
        this.gameEngine = new GameEngine(canvas.getGraphicsContext2D(), sessionManager, gameData);
        gameEngine.setGameOverCallback(this::showGameOverScreen);
        scene.setOnKeyPressed(event -> gameEngine.handleKeyPressed(event));
        scene.setOnKeyReleased(event -> gameEngine.handleKeyReleased(event));
        this.gameEngine.start();
    }

    @Override
    protected Scene createScene() {
        StackPane root = new StackPane();

        // Main game canvas
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.canvas = new Canvas(screenSize.getWidth() * 0.75, screenSize.getHeight() * 0.75);
        root.getChildren().add(canvas);

        // Pause Button
        StackPane pauseButtonContainer = new StackPane();
        Button pauseButton = new MainButton("Pause");
        pauseButton.setOnAction(e -> showPauseOverlay());
        pauseButton.setMinWidth(80);
        pauseButton.setMinHeight(30);
        pauseButton.setTranslateX(canvas.getWidth() / 2 - 50);  // Position 10px from the right
        pauseButton.setTranslateY(- canvas.getHeight() / 2 + 25);  // Position 10px from the top
        pauseButtonContainer.getChildren().add(pauseButton);

        createPauseOverlay();

        gameOverOverlay = new GameOverOverlay(uiController, sessionManager);
        gameOverOverlay.setPrefSize(canvas.getWidth(), canvas.getHeight());

        // Center overlays and pause button
        StackPane.setAlignment(pauseButtonContainer, Pos.CENTER);
        StackPane.setAlignment(pauseOverlay, Pos.CENTER);
        StackPane.setAlignment(gameOverOverlay, Pos.CENTER);

        root.getChildren().addAll(pauseButtonContainer, pauseOverlay, gameOverOverlay);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        return scene;
    }

    private void createPauseOverlay() {
        pauseOverlay = new StackPane();
        pauseOverlay.setVisible(false); // Only visible when paused
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black background
        pauseOverlay.setPrefSize(canvas.getWidth(), canvas.getHeight());

        VBox buttonContainer = new VBox(20); // Vertical spacing of 20 between buttons
        buttonContainer.setAlignment(Pos.CENTER);

        continueButton = new MenuSecondaryButton("Resume");
        continueButton.setOnAction(e -> hidePauseOverlay());

        Button saveAndQuitButton = new MenuMainButton("Save and Quit");
        saveAndQuitButton.setOnAction(e -> save_and_quit());

        Button restartButton = new MenuMainButton("Restart");
        restartButton.setOnAction(e -> {
            pauseOverlay.setVisible(false);
            gameEngine.triggerGameOver();
            uiController.gotoGamePage();
        });

        Button toggleHitBoxButton = new MenuMainButton((Settings.showHitBox? "Hide": "Show") + " Hit Box");
        toggleHitBoxButton.setOnAction(e -> {
            Settings.showHitBox = !Settings.showHitBox;
            if (Settings.showHitBox)
                toggleHitBoxButton.setText("Hide Hit Box");
            else
                toggleHitBoxButton.setText("Show Hit Box");
            hidePauseOverlay();
        });

        buttonContainer.getChildren().addAll(continueButton, saveAndQuitButton, restartButton, toggleHitBoxButton);

        pauseOverlay.getChildren().add(buttonContainer);
    }

    private void save_and_quit() {
        sessionManager.commit();
        uiController.gotoMainMenu();
    }

    private void showGameOverScreen() {
        pauseOverlay.setVisible(false);
        gameOverOverlay.loadDynamicContent(gameData);
        gameOverOverlay.setVisible(true);
        gameOverOverlay.focusRestart();
    }

    private void showPauseOverlay() {
        pauseOverlay.setVisible(true);
        continueButton.requestFocus();
        gameEngine.pause();
    }

    private void hidePauseOverlay() {
        pauseOverlay.setVisible(false);
        gameEngine.resume();
    }
}

class GameOverOverlay extends StackPane {
    private Button restartButton;
    private Button backButton;
    private UIController uiController;
    private SessionManager sessionManager;

    VBox labelBox;
    Label gameOverLabel;
    Label currentScoreLabel;
    Label highScoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label congratsLabel;

    public GameOverOverlay(UIController uiController, SessionManager sessionManager) {
        this.uiController = uiController;
        this.sessionManager = sessionManager;
        setVisible(false);
        initWidgets();
    }

    public void initWidgets() {
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        labelBox = new VBox(20);
        labelBox.setAlignment(Pos.CENTER);

        gameOverLabel = new TitleLabel("Game Over");
        currentScoreLabel = new BoldLabel("");
        highScoreLabel = new SubscriptLabel("");
        timeLabel = new BoldLabel("");
        levelLabel = new BoldLabel("");
        congratsLabel = new ColoredLabel("New High Score!", "#fbbf24");
        congratsLabel.setVisible(false);

        labelBox.getChildren().addAll(gameOverLabel, levelLabel, timeLabel, currentScoreLabel, highScoreLabel, congratsLabel);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        backButton = new MainButton("Back to Menu");
        backButton.setOnAction(e -> uiController.gotoMainMenu());

        restartButton = new MainButton("Play Again");
        restartButton.setOnAction(e -> uiController.gotoGamePage());

        Button leaderboardButton = new MainButton("Leaderboard");
        leaderboardButton.setOnAction(e -> uiController.gotoLeaderBoard());

        buttonBox.getChildren().addAll(backButton, restartButton, leaderboardButton);

        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(50));
        mainContainer.getChildren().addAll(labelBox, buttonBox);

        StackPane.setAlignment(mainContainer, Pos.CENTER);
        getChildren().add(mainContainer);
    }

    public void loadDynamicContent(GameData gameData) {
        currentScoreLabel.setText("Score: " + gameData.getScore());
        highScoreLabel.setText("High Score: " + sessionManager.getHighScore());
        levelLabel.setText("Level " + gameData.getLevel());
        timeLabel.setText(formatDuration(gameData.getGameDuration()));
        congratsLabel.setVisible(gameData.getScore() == sessionManager.getHighScore());
        highScoreLabel.setVisible(!congratsLabel.isVisible());
    }

    public void focusRestart() {
        restartButton.requestFocus();
    }

    public static String formatDuration(Duration duration) {
        long secs = duration.getSeconds();
        long mins = secs / 60;
        secs %= 60;
        return String.format("%dm%ds", mins, secs);
    }
}
