package com.deepseadevs.fisheatfish;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private final Pane gamePane;
    private Circle player;
    private final List<Circle> enemies;
    private final Random random;
    private AnimationTimer gameLoop;
    private final GamePage gamePage;
    private boolean gameSetupDone;

    public GameEngine(Pane gamePane, GamePage gamePage) {
        this.gamePane = gamePane;
        this.gamePage = gamePage;
        this.enemies = new ArrayList<>();
        this.random = new Random();
        this.gameSetupDone = false;

        // Add a layout listener to initialize game objects only after the layout is complete
        gamePane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getWidth() > 0 && newValue.getHeight() > 0 && !gameSetupDone) {
                setupGameObjects();
                startGameLoop();
                gameSetupDone = true;
            }
        });
    }

    private void setupGameObjects() {
        System.out.println("Initialized");
        // Initialize player
        player = new Circle(20, Color.BLUE);
        player.setTranslateX(gamePane.getWidth() / 2);
        player.setTranslateY(gamePane.getHeight() / 2);
        gamePane.getChildren().add(player);

        // Initialize enemies
        for (int i = 0; i < 10; i++) {
            addEnemy();
        }
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void addEnemy() {
        Circle enemy = new Circle(random.nextInt(10) - 5 + player.getRadius(), Color.RED);
        enemy.setTranslateX(random.nextDouble() * gamePane.getWidth());
        enemy.setTranslateY(random.nextDouble() * gamePane.getHeight());
        enemies.add(enemy);
        gamePane.getChildren().add(enemy);
    }

    private void update() {
        // Create a list to track enemies that need to be removed
        List<Circle> enemiesToRemove = new ArrayList<>();

        // Move enemies randomly
        for (Circle enemy : enemies) {
            double dx = random.nextDouble() * 2 - 1;
            double dy = random.nextDouble() * 2 - 1;
            enemy.setTranslateX(enemy.getTranslateX() + dx);
            enemy.setTranslateY(enemy.getTranslateY() + dy);
        }

        for (Circle enemy : enemies) {
            if (checkCollision(player, enemy)) {
                if (player.getRadius() > enemy.getRadius()) {
                    player.setRadius(player.getRadius() + enemy.getRadius() * 0.2);
                    enemiesToRemove.add(enemy);  // Mark enemy for removal
                } else {
                    gameLoop.stop();
                    // Handle game over here
                    // Call resetGame() if you want to reset the game on failure
                }
            }
        }
        enemies.removeAll(enemiesToRemove);
        for (Circle enemy : enemiesToRemove) {
            addEnemy();
        }
        gamePane.getChildren().removeAll(enemiesToRemove);
    }


    private boolean checkCollision(Circle c1, Circle c2) {
        double dx = c1.getTranslateX() - c2.getTranslateX();
        double dy = c1.getTranslateY() - c2.getTranslateY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < c1.getRadius() + c2.getRadius();
    }

    public void stopGame() {
        gameLoop.stop();
    }

    public Circle getPlayer() {
        return player;
    }
}
