package com.deepseadevs.fisheatfish.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ChristmasTreeRenderer {
    private final GraphicsContext gc;

    public ChristmasTreeRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawDecorations() {
        drawChristmasTree();
        drawOrnaments();
        drawTinsel();
        drawStar();
        drawPresents();
        drawSnow();
        drawMines();
        drawSeaweed();
    }

    private void drawChristmasTree() {
        gc.setFill(Color.DARKGREEN);
        double treeX = gc.getCanvas().getWidth() / 2 - 50;
        double treeY = gc.getCanvas().getHeight() - 200;

        gc.fillPolygon(
                new double[]{treeX, treeX + 50, treeX + 100},
                new double[]{treeY + 100, treeY, treeY + 100},
                3
        );

        gc.setFill(Color.BROWN);
        gc.fillRect(treeX + 40, treeY + 100, 20, 30);
    }

    private void drawOrnaments() {
        double treeX = gc.getCanvas().getWidth() / 2 - 50;
        double treeY = gc.getCanvas().getHeight() - 200;

        gc.setFill(Color.RED);
        gc.fillOval(treeX + 20, treeY + 50, 10, 10);
        gc.setFill(Color.YELLOW);
        gc.fillOval(treeX + 70, treeY + 50, 10, 10);
        gc.setFill(Color.BLUE);
        gc.fillOval(treeX + 45, treeY + 80, 10, 10);
        gc.setFill(Color.ORANGE);
        gc.fillOval(treeX + 10, treeY + 90, 10, 10);
        gc.setFill(Color.PURPLE);
        gc.fillOval(treeX + 80, treeY + 90, 10, 10);
    }

    private void drawTinsel() {
        double treeX = gc.getCanvas().getWidth() / 2 - 50;
        double treeY = gc.getCanvas().getHeight() - 200;

        gc.setStroke(Color.SILVER);
        gc.setLineWidth(2);
        gc.strokeLine(treeX + 10, treeY + 60, treeX + 90, treeY + 60);
        gc.strokeLine(treeX + 20, treeY + 40, treeX + 80, treeY + 40);
    }

    private void drawStar() {
        double treeX = gc.getCanvas().getWidth() / 2 - 50;
        double treeY = gc.getCanvas().getHeight() - 200;

        gc.setFill(Color.GOLD);
        gc.fillPolygon(
                new double[]{treeX + 50, treeX + 60, treeX + 65, treeX + 50, treeX + 35, treeX + 40},
                new double[]{treeY - 10, treeY + 10, treeY + 10, treeY + 20, treeY + 10, treeY + 10},
                6
        );
    }

    private void drawPresents() {
        double treeX = gc.getCanvas().getWidth() / 2 - 50;
        double treeY = gc.getCanvas().getHeight() - 200;

        gc.setFill(Color.RED);
        gc.fillRect(treeX - 30, treeY + 120, 40, 30);
        gc.setFill(Color.GREEN);
        gc.fillRect(treeX + 60, treeY + 120, 40, 30);
        gc.setFill(Color.BLUE);
        gc.fillRect(treeX + 10, treeY + 130, 40, 30);
    }

    private void drawSnow() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, gc.getCanvas().getHeight() - 50, gc.getCanvas().getWidth(), 50);
    }

    private void drawMines() {
        gc.setFill(Color.DARKGRAY);
        gc.fillOval(100, gc.getCanvas().getHeight() - 140, 30, 30);
        gc.fillOval(gc.getCanvas().getWidth() - 130, gc.getCanvas().getHeight() - 140, 30, 30);
    }

    private void drawSeaweed() {
        gc.setFill(Color.DARKGREEN);
        for (int i = 0; i < 3; i++) {
            double seaweedX = 50 + i * 100;
            double seaweedY = gc.getCanvas().getHeight() - 100;
            gc.fillPolygon(
                    new double[]{seaweedX, seaweedX + 10, seaweedX - 10},
                    new double[]{seaweedY, seaweedY - 50, seaweedY - 50},
                    3
            );
        }
    }
}
