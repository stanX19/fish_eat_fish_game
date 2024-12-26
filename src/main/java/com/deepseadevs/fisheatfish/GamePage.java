package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;
import com.deepseadevs.fisheatfish.game.GameEngine;
import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
import com.deepseadevs.fisheatfish.widgets.buttons.SecondaryButton;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class GamePage extends BasePage {
    private Canvas canvas;
    private GameEngine gameEngine;
    private Button restartButton;
    private Button backButton;
    private Pane gameOverOverlay;
    private Pane pauseOverlay;

    public GamePage(UIController uiController, SessionManager sessionManager) {
        this(uiController, sessionManager, false);
    }

    public GamePage(UIController uiController, SessionManager sessionManager, boolean continueGame) {
        this(uiController, sessionManager, continueGame ? sessionManager.getPreviousGameData() : sessionManager.createNewGameData());
    }

    public GamePage(UIController uiController, SessionManager sessionManager, GameData gameData) {
        super(uiController, sessionManager);
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
        Pane pauseButtonContainer = new Pane();
        Button pauseButton = new MainButton("Pause");
        pauseButton.setOnAction(e -> showPauseOverlay());
        pauseButton.setLayoutX(canvas.getWidth() - 90); // Position 10px from the right
        pauseButton.setLayoutY(10);                    // Position 10px from the top
        pauseButtonContainer.getChildren().add(pauseButton);
        root.getChildren().add(pauseButtonContainer);

        // Pause screen
        createPauseOverlay();
        root.getChildren().add(pauseOverlay);

        // Game over screen
        createGameOverOverlay();
        root.getChildren().add(gameOverOverlay);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        return scene;
    }


    private void createGameOverOverlay() {
        gameOverOverlay = new Pane();
        gameOverOverlay.setVisible(false); // Only visible on game over
        gameOverOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black background
        gameOverOverlay.setPrefSize(canvas.getWidth(), canvas.getHeight());

        // Leaderboard Button
        Button leaderboardButton = new MainButton("Leaderboard");
        leaderboardButton.setFont(new Font(20));
        leaderboardButton.setLayoutX(300);
        leaderboardButton.setLayoutY(200);
        leaderboardButton.setOnAction(e -> uiController.gotoLeaderBoard());

        // Restart Button
        restartButton = new MainButton("Retry");
        restartButton.setFont(new Font(20));
        restartButton.setLayoutX(300);
        restartButton.setLayoutY(270);
        restartButton.setOnAction(e -> uiController.gotoGamePage());

        // Back to Menu Button
        backButton = new MainButton("Back to Menu");
        backButton.setFont(new Font(20));
        backButton.setLayoutX(300);
        backButton.setLayoutY(340);
        backButton.setOnAction(e -> uiController.gotoMainMenu());

        gameOverOverlay.getChildren().addAll(leaderboardButton, restartButton, backButton);
    }

    private void createPauseOverlay() {
        pauseOverlay = new Pane();
        pauseOverlay.setVisible(false); // Only visible when paused
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black background
        pauseOverlay.setPrefSize(canvas.getWidth(), canvas.getHeight());

        // Continue Button
        Button continueButton = new MainButton("Resume");
        continueButton.setFont(new Font(20));
        continueButton.setLayoutX(300);
        continueButton.setLayoutY(250);
        continueButton.setOnAction(e -> hidePauseOverlay());

        // Quit Button
        Button quitButton = new SecondaryButton("Save and quit");
        quitButton.setFont(new Font(20));
        quitButton.setLayoutX(300);
        quitButton.setLayoutY(320);
        quitButton.setOnAction(e -> save_and_quit());

        pauseOverlay.getChildren().addAll(continueButton, quitButton);
    }

    private void save_and_quit() {
        sessionManager.commit();
        uiController.gotoMainMenu();
    }

    private void showGameOverScreen() {
        gameOverOverlay.setVisible(true);
        gameEngine.stop();
    }

    private void showPauseOverlay() {
        pauseOverlay.setVisible(true);
        gameEngine.pause();
    }

    private void hidePauseOverlay() {
        pauseOverlay.setVisible(false);
        gameEngine.resume();
    }
}
