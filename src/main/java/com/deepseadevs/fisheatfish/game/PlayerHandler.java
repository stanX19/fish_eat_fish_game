package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerHandler {
    private final GameData gameData;
    private final BaseFish player;
    private int prevFishEaten;
    private final Set<KeyCode> keysPressed;
    private final Bound bound;

    public PlayerHandler(GameData data, BaseFish player, Bound bound) {
        this.gameData = data;
        this.player = player;
        player.setFishEaten(data.getLevelFishEaten());
        player.setArea(data.getSize());
        player.setScore(gameData.getScore());
        this.keysPressed = new HashSet<>();
        this.prevFishEaten = player.getFishEaten();
        this.bound = bound;
    }

    public void handleKeyPressed(KeyEvent event) {
        keysPressed.add(event.getCode());
    }

    public void handleKeyReleased(KeyEvent event) {
        keysPressed.remove(event.getCode());
    }

    public int getAccumulatedFishEaten() {
        updateTotalFishEaten();
        return gameData.getFishEaten();
    }

    public int getCurrentFishEaten() {
        updateTotalFishEaten();
        return player.getFishEaten();
    }

    public void resetFishEaten() {
        updateTotalFishEaten();
        player.setFishEaten(0);
        prevFishEaten = 0;
    }

    public void syncPlayerStats() {
        updateTotalFishEaten();
        gameData.setLevelFishEaten(player.getFishEaten());
        gameData.setSize((int)Math.round(player.getArea()));
        gameData.setScore(player.getScore());
    }

    public void updateTotalFishEaten() {
        int newFishEaten = player.getFishEaten() - prevFishEaten;
        prevFishEaten = player.getFishEaten();
        if (newFishEaten <= 0)
            return;
        gameData.setFishEaten(gameData.getFishEaten() + newFishEaten);
    }

    public void updatePlayerVelocity() {
        double dx = calculateHorizontalMovement();
        double dy = calculateVerticalMovement();

        if (player.getX() <= bound.minX) { // Left
            player.setX(bound.minX);
        }
        if (player.getX() + player.getWidth() >= bound.maxX) { // Right
            player.setX(bound.maxX - player.getWidth());
        }
        if (player.getY() <= bound.minY) { // Up
            player.setY(bound.minY);
        }
        if (player.getY() + player.getHeight() >= bound.maxY) { // Down
            player.setY(bound.maxY - player.getHeight());
        }

        player.setXv(dx);
        player.setYv(dy);

        if (player.getSpeed() > player.getMaxSpeed() || dy != 0 || dx != 0)
            player.setSpeed(player.getMaxSpeed());
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
