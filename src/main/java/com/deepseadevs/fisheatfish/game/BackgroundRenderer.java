package com.deepseadevs.fisheatfish.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundRenderer {
    private final GraphicsContext gc;
    private final Image spriteSheet;

    public BackgroundRenderer(GraphicsContext gc, String spriteSheetPath) {
        this.gc = gc;
        this.spriteSheet = new Image("file:src/main/assets/sprites/pixelBackground.png" + spriteSheetPath);
    }

    public void render(double x, double y, double width, double height) {
        gc.drawImage(spriteSheet, x, y, width, height);
    }
}

