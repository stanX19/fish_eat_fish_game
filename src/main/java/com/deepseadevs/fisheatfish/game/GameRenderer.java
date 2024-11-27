package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.Duration;

// TODO:
//  create background
//  beautify display
public class GameRenderer {
    private final GraphicsContext gc;
    private final FishHandler fishHandler;
    private final GameData gameData;

    public GameRenderer(GraphicsContext gc, FishHandler fishHandler, GameData gameData) {
        this.gc = gc;
        this.fishHandler = fishHandler;
        this.gameData = gameData;
    }

    public void render() {
        clearCanvas();
        renderBackground();
        renderFish();
        renderGameStats();
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void renderBackground() {
        gc.setFill(Color.SKYBLUE); // Set background color
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void renderFish() {
        fishHandler.renderAll(gc);
    }

    private void renderGameStats() {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 20));

        // Define layout for stats
        double margin = 10;
        double lineSpacing = 25;

        // Render score at the top left
        String scoreText = "Score: " + gameData.getScore();
        gc.setFill(Color.WHITE);
        gc.fillText(scoreText, margin, margin + lineSpacing);

        // Render level with a progress bar at the bottom left
        // TODO:
        //  find a better way to indicate progress not just divide by 30
        //  prob cannot be done here, need add attribute to gameData
        String levelText = "Level: " + gameData.getLevel();
        gc.setFill(Color.GREEN);
        gc.fillText(levelText, margin, gc.getCanvas().getHeight() - margin - lineSpacing);
        renderProgressBar(gc, margin, gc.getCanvas().getHeight() - margin, gameData.getFishEaten() / 30.0);

        // Render game duration at the top right
        String gameDurationText = formatDuration(gameData.getGameDuration());
        gc.setFill(Color.WHITE);
        gc.fillText(gameDurationText, gc.getCanvas().getWidth() - margin - 50, margin + lineSpacing);
    }

    private void renderProgressBar(GraphicsContext gc, double x, double y, double progress) {
        double barWidth = 200;
        double barHeight = 20;
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y - barHeight, barWidth, barHeight);
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y - barHeight, barWidth * progress, barHeight);
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
