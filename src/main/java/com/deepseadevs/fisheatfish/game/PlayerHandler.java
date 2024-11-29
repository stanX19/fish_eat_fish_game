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
    private final BaseFish player;
    private int prevFishEaten;
    private int totalFishEaten;
    private final Set<KeyCode> keysPressed;

    public PlayerHandler(BaseFish player) {
        this.player = player;
        this.keysPressed = new HashSet<>();
        this.prevFishEaten = player.getFishEaten();
        this.totalFishEaten = player.getFishEaten();
    }

    public void handleKeyPressed(KeyEvent event) {
        keysPressed.add(event.getCode());
    }

    public void handleKeyReleased(KeyEvent event) {
        keysPressed.remove(event.getCode());
    }

    public int getAccumulatedFishEaten() {
        updateTotalFishEaten();
        return totalFishEaten;
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

    private void updateTotalFishEaten() {
        int newFishEaten = player.getFishEaten() - prevFishEaten;
        prevFishEaten = player.getFishEaten();
        if (newFishEaten <= 0)
            return;
        totalFishEaten += newFishEaten;
    }

    public void updatePlayerVelocity(double deltaTime) {
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
