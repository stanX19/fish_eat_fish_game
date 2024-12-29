package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        double boxPadding = 10;

        // Render score
        String scoreText = "Score: " + gameData.getScore();
        double scoreWidth = measureTextWidth(scoreText, gc.getFont());
        double scoreHeight = measureTextHeight(scoreText, gc.getFont());
        //gc.fillText(scoreText, margin, margin + lineSpacing);

        // Render high score
        String highScoreText = "Highscore: " + sessionManager.getHighScore();
        double highScoreWidth = measureTextWidth(highScoreText, gc.getFont());

        // Render level with progress bar
        String levelText = "Level: " + gameData.getLevel();
        double levelWidth = measureTextWidth(levelText, gc.getFont());
        double levelHeight = measureTextHeight(levelText, gc.getFont());

        // Progress bar dimensions
        double progressBarWidth = 200;
        double progressBarHeight = 10;

        // Game duration text
        String gameDurationText = formatDuration(gameData.getGameDuration());
        double durationWidth = measureTextWidth(gameDurationText, gc.getFont());
        double durationHeight = measureTextHeight(gameDurationText, gc.getFont());

        // Score and high score box dimensions
        double scoreBoxWidth = Math.max(scoreWidth, highScoreWidth) + 2 * boxPadding;
        double scoreBoxHeight = scoreHeight * 2 + lineSpacing + 2 * boxPadding;
        double scoreBoxX = margin;
        double scoreBoxY = margin;

        // Level and progress bar box dimensions
        double levelBoxWidth = Math.max(levelWidth, progressBarWidth) + 2 * boxPadding;
        double levelBoxHeight = levelHeight + lineSpacing + progressBarHeight + 2 * boxPadding;
        double levelBoxX = margin;
        double levelBoxY = gc.getCanvas().getHeight() - levelBoxHeight - margin;

        // Time duration box dimensions
        double durationBoxWidth = durationWidth + 2 * boxPadding;
        double durationBoxHeight = durationHeight + 2 * boxPadding;
        double pauseButtonWidth = 50;
        double durationBoxX = gc.getCanvas().getWidth() - MARGIN - pauseButtonWidth - 100;
        double durationBoxY = MARGIN;

        // Render score and high score box
        gc.setFill(Color.rgb(50, 50, 50, 0.5)); // Semi-transparent grey
        gc.fillRect(scoreBoxX, scoreBoxY, scoreBoxWidth, scoreBoxHeight);

        gc.setFill(Color.WHITE);
        gc.fillText(scoreText, scoreBoxX + boxPadding, scoreBoxY + boxPadding + scoreHeight);
        gc.fillText(highScoreText, scoreBoxX + boxPadding, scoreBoxY + boxPadding + scoreHeight + lineSpacing);

        // Render level and progress bar box
        gc.setFill(Color.rgb(50, 50, 50, 0.5)); // Semi-transparent grey
        gc.fillRect(levelBoxX, levelBoxY, levelBoxWidth, levelBoxHeight);

        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        gc.fillText(levelText, levelBoxX + boxPadding, levelBoxY + boxPadding + levelHeight);

        renderProgressBar(levelBoxX + boxPadding, levelBoxY + levelBoxHeight - progressBarHeight - boxPadding, gameData.getProgress());

        // Render game duration
        gc.setFill(Color.rgb(50, 50, 50, 0.5)); // Semi-transparent grey
        gc.fillRect(durationBoxX, durationBoxY, durationBoxWidth, durationBoxHeight);
        gc.setFill(Color.WHITE);
        gc.fillText(gameDurationText, durationBoxX + boxPadding, durationBoxY + boxPadding + durationHeight);
    }

    private double measureTextWidth(String text, Font font) {
        Text tempText = new Text(text);
        tempText.setFont(font);
        return tempText.getLayoutBounds().getWidth();
    }

    // Helper method to measure text height
    private double measureTextHeight(String text, Font font) {
        Text tempText = new Text(text);
        tempText.setFont(font);
        return tempText.getLayoutBounds().getHeight();
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