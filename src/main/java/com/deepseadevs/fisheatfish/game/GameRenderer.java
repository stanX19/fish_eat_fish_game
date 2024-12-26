package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.SessionManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import java.time.Duration;

public class GameRenderer {
    private static final double SAND_HEIGHT = 100;
    private static final double TREE_WIDTH = 128;
    private static final double TREE_HEIGHT = 128;
    private static final double PROGRESS_BAR_HEIGHT = 10;
    private static final double MARGIN = 10;
    private static final double LINE_SPACING = 25;

    private final GraphicsContext gc;
    private final FishHandler fishHandler;
    private final GameData gameData;
    private final SessionManager sessionManager;
    private final ChristmasTreeRenderer christmasTreeRenderer;

    public GameRenderer(GraphicsContext gc, FishHandler fishHandler, GameData gameData, SessionManager sessionManager) {
        this.gc = gc;
        this.fishHandler = fishHandler;
        this.gameData = gameData;
        this.sessionManager = sessionManager;
        this.christmasTreeRenderer = new ChristmasTreeRenderer(gc, "ChristmasTree.png");
    }

    public void render() {
        renderBackground();
        renderFish();
        renderGameStats();
        renderChristmasTree();
    }
    private void renderBackground() {
        clearCanvas();
        drawGradientBackground();
        drawWavyBackground();
        drawLightRays();
        drawSand();
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void drawGradientBackground() {
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.SKYBLUE),
                new Stop(1, Color.DARKBLUE)
        );
        gc.setFill(gradient);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void drawWavyBackground() {
        gc.setFill(Color.rgb(0, 100, 255, 0.1));
        for (int i = 0; i < 5; i++) {
            double offsetY = i * 50;
            gc.fillOval(-50, offsetY, gc.getCanvas().getWidth() + 100, 80);
        }
    }

    private void drawLightRays() {
        gc.setFill(Color.rgb(255, 255, 255, 0.2));
        for (int i = 0; i < 5; i++) {
            double startX = 50 + i * 100;
            double endX = startX + 100;
            gc.fillPolygon(
                    new double[]{startX, endX, gc.getCanvas().getWidth() / 2},
                    new double[]{0, 0, gc.getCanvas().getHeight()},
                    3
            );
        }
    }

    private void drawSand() {
        gc.setFill(Color.SANDYBROWN);
        gc.fillRect(0, gc.getCanvas().getHeight() - 100, gc.getCanvas().getWidth(), 100);
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

    private void renderChristmasTree() {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        double treeX = canvasWidth / 2 - TREE_WIDTH / 2;
        double treeY = canvasHeight - SAND_HEIGHT - TREE_HEIGHT;

        christmasTreeRenderer.render(treeX, treeY, TREE_WIDTH, TREE_HEIGHT);
    }
}