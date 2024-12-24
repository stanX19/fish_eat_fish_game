package com.deepseadevs.fisheatfish.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Animation {
    private WritableImage[] frames;
    private int currentFrame;
    private double frameTime; // Seconds per frame
    private double elapsedTime;
    private final int totalFrames;

    // TODO:
    //  add image resizing to make rendered image grow with fish size
    //  sprite width != fish width, need to consider the offset
    public Animation(String spritePath) {
        this(spritePath, 0.5);
    }

    public Animation(String spritePath, double frameTime) {
        this.frameTime = frameTime;
        Image spriteSheet = new Image(spritePath);
        int frameWidth = (int) spriteSheet.getWidth();
        // Assuming square frames
        totalFrames = (int) (spriteSheet.getHeight() / frameWidth);

        frames = new WritableImage[totalFrames];
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = new WritableImage(reader, 0, i * frameWidth, frameWidth, frameWidth);
        }
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= frameTime) {
            currentFrame = (currentFrame + (int)(elapsedTime / frameTime)) % totalFrames;
            elapsedTime %= frameTime;
        }
    }

    public void render(GraphicsContext gc, double x, double y) {
        gc.drawImage(frames[currentFrame], x, y);
    }
}
