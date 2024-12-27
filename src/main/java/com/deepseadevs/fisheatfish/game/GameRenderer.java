package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.SessionManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import java.time.Duration;

public class GameRenderer {
    private static final double PROGRESS_BAR_HEIGHT = 10;
    private static final double MARGIN = 10;
    private static final double LINE_SPACING = 25;

    private final GraphicsContext gc;
    private final FishHandler fishHandler;
    private final GameData gameData;
    private final SessionManager sessionManager;
    private final Image backgroundRenderer;

    public GameRenderer(GraphicsContext gc, FishHandler fishHandler, GameData gameData, SessionManager sessionManager) {
        this.gc = gc;
        this.fishHandler = fishHandler;
        this.gameData = gameData;
        this.sessionManager = sessionManager;
        this.backgroundRenderer = new Image("file:src/main/assets/sprites/pixelBackground.png");
    }

    public void render() {
        renderBackground();
        renderFish();
        renderGameStats();

    }

    private void renderBackground() {
        clearCanvas();
        gc.drawImage(backgroundRenderer, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void renderFish() {
        fishHandler.renderAll(gc);
    }

    private void renderGameStats() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));

        double margin = 10;
        double lineSpacing = 25;

        // Render score
        String scoreText = "Score: " + gameData.getScore();
        gc.fillText(scoreText, margin, margin + lineSpacing);

        // Render high score
        String highScoreText = "Highscore: " + sessionManager.getHighScore();
        gc.fillText(highScoreText, margin, margin + 2 * lineSpacing);

        // Render level with progress bar
        String levelText = "Level: " + gameData.getLevel();
        gc.setFill(Color.GREEN);
        gc.fillText(levelText, margin, gc.getCanvas().getHeight() - margin - lineSpacing);
        renderProgressBar(margin, gc.getCanvas().getHeight() - margin, gameData.getProgress());

        // Render game duration
        String gameDurationText = formatDuration(gameData.getGameDuration());
        double pauseButtonWidth = 50; // Assume the pause button width is 50
        double gameDurationX = gc.getCanvas().getWidth() - MARGIN - pauseButtonWidth - 100; // Place to the left of the pause button
        gc.setFill(Color.WHITE);
        gc.fillText(gameDurationText, gameDurationX, MARGIN + LINE_SPACING);
    }

    private void renderProgressBar(double x, double y, double progress) {
        double barWidth = 200;
        double barHeight = 10;

        // Ensure progress is within [0, 1]
        progress = Math.max(0, Math.min(1, progress));

        // Draw background
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y - barHeight, barWidth, barHeight);

        // Draw progress
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y - barHeight, barWidth * progress, barHeight);
    }

    private String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d", minutes, seconds);
    }

}