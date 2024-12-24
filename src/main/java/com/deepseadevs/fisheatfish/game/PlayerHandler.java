package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

// TODO:
//  bound player movement if moving out of bounds
//  prob need take in class Bound
//  bound by setting respective velocity to zero
public class PlayerHandler {
    private final GameData gameData;
    private final BaseFish player;
    private int prevFishEaten;
    private final Set<KeyCode> keysPressed;

    public PlayerHandler(GameData data, BaseFish player) {
        this.gameData = data;
        this.player = player;
        player.setFishEaten(data.getLevelFishEaten());
        player.setArea(data.getSize());
        this.keysPressed = new HashSet<>();
        this.prevFishEaten = player.getFishEaten();
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
        gameData.setSize((int) player.getArea());
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
