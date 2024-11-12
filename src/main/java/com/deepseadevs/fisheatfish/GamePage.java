package com.deepseadevs.fisheatfish;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GamePage extends BasePage {
    private Canvas canvas;
    private GameEngine gameEngine;
    Button restartButton;

    public GamePage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
        this.gameEngine = new GameEngine(canvas.getGraphicsContext2D());
        gameEngine.setGameOverCallback(() -> restartButton.setVisible(true));
        scene.setOnKeyPressed(event -> gameEngine.handleKeyPressed(event));
        scene.setOnKeyReleased(event -> gameEngine.handleKeyReleased(event));
        this.gameEngine.start();
    }

    @Override
    protected Scene createScene() {
        StackPane root = new StackPane();

        this.canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        restartButton = new Button("Restart");
        restartButton.setOnAction(e -> uiController.gotoGamePage());
        restartButton.setFont(new Font(20));
        restartButton.setVisible(false); // Only show on game over
        root.getChildren().add(restartButton);

        return scene;
    }
    private void showGameOverScreen() {
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> uiController.gotoGamePage()); // Restart game
        restartButton.setFont(new Font(20));

        // Add restart button to the StackPane and ensure it’s only done once
        StackPane root = (StackPane) scene.getRoot();
        if (!root.getChildren().contains(restartButton)) { // Check if button is already added
            root.getChildren().add(restartButton);
        }
    }
}
