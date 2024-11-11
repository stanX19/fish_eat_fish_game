package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GamePage extends BasePage {
    private GameEngine gameEngine;
    private Pane gamePane;

    public GamePage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        gamePane = new Pane();
        gamePane.setPrefSize(800, 600);

        // Initialize the game engine
        gameEngine = new GameEngine(gamePane, this);

        // Add back button to return to main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> {
            gameEngine.stopGame();
            uiController.gotoMainMenu();
        });
        backButton.setLayoutX(700);
        backButton.setLayoutY(10);
        gamePane.getChildren().add(backButton);

        Scene scene = new Scene(gamePane, 800, 600);
        setupControls(scene);
        return scene;
    }

    private void setupControls(Scene scene) {
        scene.setOnKeyPressed(e -> {
            double speed = 20.0;
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                gameEngine.getPlayer().setTranslateY(gameEngine.getPlayer().getTranslateY() - speed);
            } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                gameEngine.getPlayer().setTranslateY(gameEngine.getPlayer().getTranslateY() + speed);
            } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                gameEngine.getPlayer().setTranslateX(gameEngine.getPlayer().getTranslateX() - speed);
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                gameEngine.getPlayer().setTranslateX(gameEngine.getPlayer().getTranslateX() + speed);
            }
        });
    }
}
