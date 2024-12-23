package com.deepseadevs.fisheatfish.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Animation {
    private Image spriteSheet;
    private WritableImage[] frames;
    private int currentFrame;
    private double frameTime;
    private double elapsedTime;
    private AnimationTimer animationTimer;
    private final int frameWidth;
    private final int frameHeight;
    private final int totalFrames;

    public Animation(String spritePath) {
        this.spriteSheet = new Image(spritePath);
        this.frameWidth = (int) spriteSheet.getWidth();
        this.frameHeight = frameWidth;
        this.totalFrames = (int) (spriteSheet.getHeight() / frameHeight);

        this.frames = new WritableImage[totalFrames];

        // Extract frames from the sprite sheet
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < totalFrames; i++) {
            this.frames[i] = new WritableImage(reader, 0, i * frameHeight, frameWidth, frameHeight);
        }

        this.currentFrame = 0;
        this.frameTime = 0.5; // 0.5 seconds per frame for 2 frames per second
        this.elapsedTime = 0;

        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - previousTime) / 1_000_000_000.0;
                update(deltaTime);
                previousTime = now;
            }
            private long previousTime = System.nanoTime();
        };
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= frameTime) {
            elapsedTime = 0;
            currentFrame = (currentFrame + 1) % totalFrames;
        }
    }

    public void render(GraphicsContext gc, double x, double y) {
        gc.drawImage(frames[currentFrame], x, y);
    }
}


