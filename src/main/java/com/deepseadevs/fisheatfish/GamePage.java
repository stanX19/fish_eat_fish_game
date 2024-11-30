package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameEngine;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GamePage extends BasePage {
    private Canvas canvas;
    private GameEngine gameEngine;
    private Button restartButton;
    private Button backButton;
    private Pane gameOverOverlay;

    public GamePage(UIController uiController, SessionManager sessionManager) {
        this(uiController, sessionManager, false);
    }

    public GamePage(UIController uiController, SessionManager sessionManager, boolean continueGame) {
        super(uiController, sessionManager);
        this.gameEngine = new GameEngine(canvas.getGraphicsContext2D(), sessionManager, continueGame);
        gameEngine.setGameOverCallback(this::showGameOverScreen);
        scene.setOnKeyPressed(event -> gameEngine.handleKeyPressed(event));
        scene.setOnKeyReleased(event -> gameEngine.handleKeyReleased(event));
        this.gameEngine.start();
    }

    // TODO:
    //  add button to pause, and show overlay that contains
    //  buttons for continue or quit mid game
    // TODO:
    //  remake ending screen
    //  - goto leaderboard, retry, back to menu
    @Override
    protected Scene createScene() {
        StackPane root = new StackPane();

        // Main game canvas
        this.canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        // Game over screen
        gameOverOverlay = new Pane();
        gameOverOverlay.setVisible(false); // Only visible on game over
        gameOverOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // semi-transparent black background
        gameOverOverlay.setPrefSize(800, 600);
        restartButton = new Button("Restart");
        restartButton.setOnAction(e -> uiController.gotoGamePage());
        restartButton.setFont(new Font(20));
        restartButton.setLayoutX(300);
        restartButton.setLayoutY(250);
        backButton = new Button("Back");
        backButton.setOnAction(e -> uiController.gotoMainMenu());
        backButton.setFont(new Font(20));
        backButton.setLayoutX(300);
        backButton.setLayoutY(320);
        gameOverOverlay.getChildren().addAll(restartButton, backButton);
        root.getChildren().addAll(gameOverOverlay);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        return scene;
    }

    private void showGameOverScreen() {
        gameOverOverlay.setVisible(true);
    }
}
