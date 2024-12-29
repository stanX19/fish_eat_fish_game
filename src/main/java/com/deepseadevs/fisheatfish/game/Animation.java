package com.deepseadevs.fisheatfish.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Animation {
    private final WritableImage[] frames;
    private final int totalFrames;
    private final Bound spriteDimension;
    private int currentFrame;
    private final double frameTime; // Seconds per frame
    private double elapsedTime;
    
    public Animation(String spritePath) {
        this(spritePath, 0.5);
    }

    public Animation(String spritePath, double frameTime) {
        this(spritePath, frameTime, null);
    }

    public Animation(String spritePath, double frameTime, Bound spriteDimension) {
        this.frameTime = frameTime;
        Image spriteSheet = new Image(spritePath);

        int frameWidth = (int) spriteSheet.getWidth();
        int frameHeight = frameWidth; // Assuming square frames
        totalFrames = (int) (spriteSheet.getHeight() / frameHeight);

        if (spriteSheet.getHeight() < frameWidth) {
            throw new IllegalArgumentException("Cannot process sprite sheet with height < width");
        }

        frames = new WritableImage[totalFrames];
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = new WritableImage(reader, 0, i * frameHeight, frameWidth, frameHeight);
        }
        this.spriteDimension = spriteDimension != null ? spriteDimension : generateDimension(frames[0]);
        double renderWidth = this.spriteDimension.getWidth();
        double renderHeight = this.spriteDimension.getHeight();
        if (renderWidth > frameWidth || renderHeight > frameHeight) {
            throw new IllegalArgumentException("Sprite dimension exceeds detected bounds");
        }
    }

    // Generate bounds based on first and last non-transparent pixels
    // on the x-axis and y-axis
    public Bound generateDimension(Image frame) {
        PixelReader pixelReader = frame.getPixelReader();
        int frameWidth = (int) frame.getWidth();
        int frameHeight = (int) frame.getHeight();
        int minX = frameWidth;
        int minY = frameHeight;
        int maxX = 0;
        int maxY = 0;

        for (int y = 0; y < frameHeight; y++) {
            for (int x = 0; x < frameWidth; x++) {
                if (pixelReader.getColor(x, y).getOpacity() > 0) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
        return new Bound(minX, minY, maxX, maxY);
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= frameTime) {
            currentFrame = (currentFrame + (int) (elapsedTime / frameTime)) % totalFrames;
            elapsedTime %= frameTime;
        }
    }

    public void render(GraphicsContext gc, double x, double y) {
        render(gc, x, y, spriteDimension.getWidth(), spriteDimension.getHeight());
    }

    public void render(GraphicsContext gc, double x, double y, double width, double height) {
        double scaleX = width / spriteDimension.getWidth();
        double scaleY = height / spriteDimension.getHeight();

        gc.save();
        gc.translate(x - spriteDimension.minX, y - spriteDimension.minY);
        gc.scale(scaleX, scaleY);
        gc.drawImage(frames[currentFrame], 0, 0);
        gc.restore();

        gc.save();
        gc.setStroke(Color.LIME); // Set the color of the rectangle (example: red)
        gc.setLineWidth(2); // Set the width of the rectangle's border
        gc.strokeRect(x, y, width, height); // Draw the rectangle
        gc.restore();
    }

    public Bound getSpriteDimension() {
        return this.spriteDimension;
    }
}
