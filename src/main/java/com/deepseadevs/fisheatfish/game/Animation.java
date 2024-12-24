package com.deepseadevs.fisheatfish.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Affine;

public class Animation {
    private WritableImage[] frames;
    private int currentFrame;
    private double frameTime; // Seconds per frame
    private double elapsedTime;
    private final int totalFrames;
    private double renderWidth;  // Desired width of the sprite
    private double renderHeight; // Desired height of the sprite

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
        totalFrames = (int) (spriteSheet.getHeight() / frameWidth);

        frames = new WritableImage[totalFrames];
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = new WritableImage(reader, 0, i * frameWidth, frameWidth, frameWidth);
        }

        // Default render dimensions match the sprite's intrinsic dimensions
        renderWidth = frames[0].getWidth();
        renderHeight = frames[0].getHeight();
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= frameTime) {
            currentFrame = (currentFrame + (int) (elapsedTime / frameTime)) % totalFrames;
            elapsedTime %= frameTime;
        }
    }

    public void render(GraphicsContext gc, double x, double y) {
        gc.save(); // Save the current state of the GraphicsContext

        // Apply transformations to scale and position the sprite
        Affine transform = new Affine();
        transform.appendTranslation(x, y);
        transform.appendScale(renderWidth / frames[currentFrame].getWidth(),
                renderHeight / frames[currentFrame].getHeight());
        gc.setTransform(transform);

        // Draw the current frame
        gc.drawImage(frames[currentFrame], 0, 0);

        gc.restore(); // Restore the original state of the GraphicsContext
    }

    // Getter and Setter for render dimensions
    public double getRenderWidth() {
        return renderWidth;
    }

    public void setRenderWidth(double renderWidth) {
        this.renderWidth = renderWidth;
    }

    public double getRenderHeight() {
        return renderHeight;
    }

    public void setRenderHeight(double renderHeight) {
        this.renderHeight = renderHeight;
    }

    public void setRenderDimensions(double width, double height) {
        this.renderWidth = width;
        this.renderHeight = height;
    }
}
//private WritableImage[] frames;
//private int currentFrame;
//private double frameTime; // Seconds per frame
//private double elapsedTime;
//private final int totalFrames;
//

//public Animation(String spritePath) {
//    this(spritePath, 0.5);
//}
//
//public Animation(String spritePath, double frameTime) {
//    this.frameTime = frameTime;
//    Image spriteSheet = new Image(spritePath);
//    int frameWidth = (int) spriteSheet.getWidth();
//    // Assuming square frames
//    totalFrames = (int) (spriteSheet.getHeight() / frameWidth);
//
//    frames = new WritableImage[totalFrames];
//    PixelReader reader = spriteSheet.getPixelReader();
//    for (int i = 0; i < totalFrames; i++) {
//        frames[i] = new WritableImage(reader, 0, i * frameWidth, frameWidth, frameWidth);
//    }
//}
//
//public void update(double deltaTime) {
//    elapsedTime += deltaTime;
//    if (elapsedTime >= frameTime) {
//        currentFrame = (currentFrame + (int)(elapsedTime / frameTime)) % totalFrames;
//        elapsedTime %= frameTime;
//    }
//}
//
//public void render(GraphicsContext gc, double x, double y) {
//    gc.drawImage(frames[currentFrame], x, y);
//}
