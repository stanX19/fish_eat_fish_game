package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.database.SessionManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.Duration;

public class GameRenderer {
    private static final double PROGRESS_BAR_HEIGHT = 10;
    private static final double MARGIN = 10;
    private static final double LINE_SPACING = 25;
    private static final double BOX_PADDING = 5;
    private static final Color BOX_COLOR = Color.rgb(30, 30, 30, 0.3); // Semi-transparent grey
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font DEFAULT_FONT = new Font("HP Simplified", 20);

    private final GraphicsContext gc;
    private final FishHandler fishHandler;
    private final GameData gameData;
    private final SessionManager sessionManager;
    private final Image backgroundImage;

    public GameRenderer(GraphicsContext gc, FishHandler fishHandler, GameData gameData, SessionManager sessionManager) {
        this.gc = gc;
        this.fishHandler = fishHandler;
        this.gameData = gameData;
        this.sessionManager = sessionManager;
        this.backgroundImage = new Image("file:src/assets/pixelBackground.png");
    }

    public void render() {
        renderBackground();
        renderFish();
        renderGameStats();
    }

    private void renderBackground() {
        clearCanvas();
        gc.drawImage(backgroundImage, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void renderFish() {
        fishHandler.renderAll(gc);
        if (Settings.showHitBox)
            fishHandler.renderAllHitBox(gc);
    }

    private void renderGameStats() {
        gc.setFill(TEXT_COLOR);
        gc.setFont(DEFAULT_FONT);
        renderScoreBox();
        renderLevelBox();
        renderDurationBox();
    }

    private void renderScoreBox() {
        String scoreText = "Score: " + gameData.getScore();
        String highScoreText = "Highscore: " + sessionManager.getHighScore();

        // Measure text dimensions
        double scoreWidth = measureTextWidth(scoreText, gc.getFont());
        double highScoreWidth = measureTextWidth(highScoreText, gc.getFont());
        double textHeight = measureTextHeight(scoreText, gc.getFont());

        // Calculate box dimensions
        double boxWidth = Math.max(scoreWidth, highScoreWidth) + 2 * BOX_PADDING; // Add padding
        double boxHeight = textHeight * 2 + (LINE_SPACING / 2) + 2 * BOX_PADDING - 5;

        // Box position
        double boxX = MARGIN;
        double boxY = MARGIN ;

        // Draw the box
        gc.setFill(BOX_COLOR);
        gc.fillRect(boxX, boxY, boxWidth, boxHeight - 15);

        // Draw the text
        gc.setFill(TEXT_COLOR);
        gc.fillText(scoreText, boxX + BOX_PADDING, boxY + BOX_PADDING + textHeight - 10);
        gc.fillText(highScoreText, boxX + BOX_PADDING, boxY + BOX_PADDING + textHeight + LINE_SPACING - 10);
    }

    private void renderLevelBox() {
        String levelText = "Level: " + gameData.getLevel();
        double levelWidth = measureTextWidth(levelText, gc.getFont());
        double levelHeight = measureTextHeight(levelText, gc.getFont());

        // Progress bar dimensions
        double progressBarWidth = 200;

        // Calculate box dimensions
        double boxWidth = Math.max(levelWidth, progressBarWidth) + 2 * BOX_PADDING; // Add padding
        double boxHeight = levelHeight + (LINE_SPACING / 2) + PROGRESS_BAR_HEIGHT + 2 * BOX_PADDING; // Add padding

        // Box position
        double boxX = MARGIN;
        double boxY = gc.getCanvas().getHeight() - boxHeight - MARGIN;

        // Draw the box
        gc.setFill(BOX_COLOR);
        gc.fillRect(boxX, boxY, boxWidth, boxHeight - 10);

        // Draw the level text
        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        gc.fillText(levelText, boxX + BOX_PADDING, boxY + BOX_PADDING + levelHeight - 10);

        // Draw the progress bar
        renderProgressBar(boxX + BOX_PADDING, boxY + boxHeight - PROGRESS_BAR_HEIGHT - BOX_PADDING, gameData.getProgress());
    }

    private void renderDurationBox() {
        String gameDurationText = formatDuration(gameData.getGameDuration());
        double durationWidth = measureTextWidth(gameDurationText, gc.getFont());
        double durationHeight = measureTextHeight(gameDurationText, gc.getFont());

        // Calculate box dimensions
        double boxWidth = durationWidth + 2 * BOX_PADDING; // Add padding
        double boxHeight = durationHeight + 2 * BOX_PADDING; // Add padding

        // Box position
        double pauseButtonWidth = 50;
        double boxX = gc.getCanvas().getWidth() - MARGIN - pauseButtonWidth - 100;
        double boxY = MARGIN;

        // Draw the box
        gc.setFill(BOX_COLOR);
        gc.fillRect(boxX, boxY, boxWidth, boxHeight - 5);

        // Draw the duration text
        gc.setFill(TEXT_COLOR);
        gc.fillText(gameDurationText, boxX + BOX_PADDING, boxY + BOX_PADDING + durationHeight - 8);
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
        double barHeight = PROGRESS_BAR_HEIGHT;

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