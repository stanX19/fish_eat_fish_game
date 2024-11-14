package com.deepseadevs.fisheatfish;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PlayerHandler {
    private final BaseFish player;
    private final Set<KeyCode> keysPressed;

    public PlayerHandler(BaseFish player) {
        this.player = player;
        this.keysPressed = new HashSet<>();
    }

    public void handleKeyPressed(KeyEvent event) {
        keysPressed.add(event.getCode());
    }

    public void handleKeyReleased(KeyEvent event) {
        keysPressed.remove(event.getCode());
    }

    public void updatePlayerMovement(double deltaTime) {
        double dx = calculateHorizontalMovement();
        double dy = calculateVerticalMovement();

        player.setXv(dx);
        player.setYv(dy);
        if (player.getSpeed() > player.getMaxSpeed() || dy != 0 || dx != 0)
            player.setSpeed(player.getMaxSpeed());
    }

    private double calculateHorizontalMovement() {
        if (keysPressed.contains(KeyCode.A) || keysPressed.contains(KeyCode.LEFT))
            return -1;
        if (keysPressed.contains(KeyCode.D) || keysPressed.contains(KeyCode.RIGHT))
            return 1;
        return 0;
    }

    private double calculateVerticalMovement() {
        if (keysPressed.contains(KeyCode.W) || keysPressed.contains(KeyCode.UP))
            return -1;
        if (keysPressed.contains(KeyCode.S) || keysPressed.contains(KeyCode.DOWN))
            return 1;
        return 0;
    }
}


public class GameEngine {
    private final GraphicsContext gc;
    private final BaseFish player;
    private final List<BaseFish> enemies;
    private AnimationTimer gameLoop;
    private Runnable gameOverCallback;
    private boolean gameOver;
    private final PlayerHandler playerHandler;

    public GameEngine(GraphicsContext gc) {
        this.gc = gc;
        this.player = new BaseFish(200, 200, 500, 20, 20);
        this.enemies = new ArrayList<>();
        this.gameOver = false;
        this.playerHandler = new PlayerHandler(player);
        spawnEnemies(15);
        initializeGameLoop();
    }

    public void setGameOverCallback(Runnable gameOverCallback) {
        this.gameOverCallback = gameOverCallback;
    }

    public void start() {
        gameLoop.start();
    }

    public void handleKeyPressed(KeyEvent event) {
        playerHandler.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        playerHandler.handleKeyReleased(event);
    }

    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (!gameOver) {
                    update(deltaTime);
                    render();
                }
            }
        };
    }

    private void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            enemies.add(new BaseFish(Math.random() * 780, Math.random() * 580, 50,
                    player.width + (Math.random() - 0.5) * 10, 15));
        }
    }

    private void update(double deltaTime) {
        playerHandler.updatePlayerMovement(deltaTime);
        player.update(deltaTime);
        checkCollisions();
    }

    private void render() {
        gc.clearRect(0, 0, 800, 600);
        renderPlayer();
        renderEnemies();
    }

    private void checkCollisions() {
        for (BaseFish enemy : new ArrayList<>(enemies)) {
            if (player.collidesWith(enemy)) {
                handleCollisionWithEnemy(enemy);
            }
        }
    }

    private void handleCollisionWithEnemy(BaseFish enemy) {
        if (player.getWidth() > enemy.getWidth()) {
            enemies.remove(enemy);
            player.setWidth(player.width + enemy.width / 5.0);
            spawnEnemies(1);
        } else {
            triggerGameOver();
        }
    }

    private void triggerGameOver() {
        gameOver = true;
        if (gameOverCallback != null) {
            gameOverCallback.run();
        }
    }

    private void renderPlayer() {
        player.render(gc);
    }

    private void renderEnemies() {
        for (BaseFish enemy : enemies) {
            enemy.render(gc);
        }
    }
}