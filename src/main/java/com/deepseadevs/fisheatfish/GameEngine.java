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
        player.move(deltaTime);
    }

    private double calculateHorizontalMovement() {
        if (keysPressed.contains(KeyCode.A) || keysPressed.contains(KeyCode.LEFT)) return -1;
        if (keysPressed.contains(KeyCode.D) || keysPressed.contains(KeyCode.RIGHT)) return 1;
        return 0;
    }

    private double calculateVerticalMovement() {
        if (keysPressed.contains(KeyCode.W) || keysPressed.contains(KeyCode.UP)) return -1;
        if (keysPressed.contains(KeyCode.S) || keysPressed.contains(KeyCode.DOWN)) return 1;
        return 0;
    }
}


public class GameEngine {
    private final GraphicsContext gc;
    private final BaseFish player;
    private final List<BaseFish> enemies;
    private final PlayerHandler playerHandler;
    private AnimationTimer gameLoop;
    private Runnable gameOverCallback;
    private boolean gameOver;

    public GameEngine(GraphicsContext gc) {
        this.gc = gc;
        this.player = new BaseFish(200, 200, 300, 30, 20);
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

    private void spawnEnemy() {
        BaseFish newFish = new BaseFish(-player.getWidth() - Math.random() * 200, Math.random() * 580,
                500, player.getWidth(), player.getHeight());
        newFish.setArea(player.getArea() * Math.random() + 100);
        newFish.setXv(50 + Math.random() * 150);
        newFish.setYv(10 * Math.random() - 5);
        enemies.add(newFish);
    }

    private void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            spawnEnemy();
        }
    }

    public double getCanvasWidth() {
        return gc.getCanvas().getWidth();
    }

    public double getCanvasHeight() {
        return gc.getCanvas().getWidth();
    }

    private void boundFishMovement(BaseFish fish) {
        boundFishMovement(fish, 0, getCanvasWidth(), 0, getCanvasHeight());
    }
    
    private void boundFishMovement(BaseFish fish, double minX, double maxX, double minY, double maxY) {
        if (fish.getX() + fish.getWidth() > maxX && fish.getXv() > 0)
            fish.setXv(-fish.getXv());
        if (fish.getX() < minX && fish.getXv() < 0)
            fish.setXv(-fish.getXv());
        if (fish.getY() + fish.getHeight() > maxY && fish.getYv() > 0)
            fish.setYv(-fish.getYv());
        if (fish.getY() < minY && fish.getYv() < 0)
            fish.setYv(-fish.getYv());
    }
    
    private void moveEnemies(double deltaTime) {
        for (BaseFish enemy: enemies) {
            enemy.move(deltaTime);
            boundFishMovement(enemy, -100, getCanvasWidth() + 100, 0, getCanvasHeight());
        }
    }

    private void update(double deltaTime) {
        playerHandler.updatePlayerMovement(deltaTime);
        boundFishMovement(player);
        player.update(deltaTime);
        moveEnemies(deltaTime);
        checkCollisions();
    }

    private void render() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        renderPlayer();
        renderEnemies();
    }

    private void handleFishFishCollision(BaseFish fish1, BaseFish fish2) {
        if (Math.abs(fish1.getArea() - fish2.getArea()) < 10) {
            return;
        }
        if (fish1.getArea() > fish2.getArea()) {
            enemies.remove(fish2);
            fish1.setArea(fish1.getArea() + fish2.getArea() * 0.5);
        } else {
            enemies.remove(fish1);
            fish1.setArea(fish2.getArea() + fish1.getArea() * 0.5);
        }
        spawnEnemies(1);
    }

    private void checkCollisions() {
        for (BaseFish enemy : new ArrayList<>(enemies)) {
            if (player.collidesWith(enemy)) {
                handleCollisionWithEnemy(enemy);
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                BaseFish fish1 = enemies.get(i);
                BaseFish fish2 = enemies.get(j);
                if (fish1.collidesWith(fish2))
                    handleFishFishCollision(fish1, fish2);
            }
        }
    }

    private void handleCollisionWithEnemy(BaseFish enemy) {
        if (player.getArea() > enemy.getArea()) {
            enemies.remove(enemy);
            player.setArea(player.getArea() + enemy.getArea() * 0.1);
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