package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SmallPlayerFish extends BasePlayerFish {

    private Animation fishAnimation;
    protected String rightSpriteSheetPath = "/src/main/java/fishmodel/redrightsprite.png";
    protected String leftSpriteSheetPath = "/src/main/java/fishmodel/redleftsprite.png";

    public SmallPlayerFish() {
        try {
            // Handle initial direction (including case where getXv() is 0)
            if (getXv() >= 0) {
                fishAnimation = new Animation(rightSpriteSheetPath);
            } else {
                fishAnimation = new Animation(leftSpriteSheetPath);
            }
        } catch (Exception e) {
            System.err.println("Error loading initial sprite sheet: " + e.getMessage());
        }

        // Start the animation only after successful creation
        if (fishAnimation != null) {
            fishAnimation.start();
        }
    }



    @Override
    public void render(GraphicsContext gc) {
        // Update animation based on current velocity
        if (getXv() > 0 && fishAnimation != null) {
            fishAnimation.stop();
            try {
                fishAnimation = new Animation(rightSpriteSheetPath);
            } catch (Exception e) {
                System.err.println("Error loading right sprite sheet: " + e.getMessage());

            }
            fishAnimation.start();
        } else if (getXv() < 0 && fishAnimation != null) {
            fishAnimation.stop();
            try {
                fishAnimation = new Animation(leftSpriteSheetPath);
            } catch (Exception e) {
                System.err.println("Error loading left sprite sheet: " + e.getMessage());

            }
            fishAnimation.start();
        }

        // Render the current frame of the animation
        fishAnimation.render(gc, getX(), getY());
    }

    @Override
    public double getMaxSpeed() {
        return getWidth() * 3;
    }
}

//        if(getXv() < 0){
//        facingRight = false;
//    }
//        else if(getXv() > 0){
//        facingRight = true;
//    }
//
//        if (facingRight) {
//        drawFishRight(gc);
//    } else {
//        drawFishLeft(gc);
//    }
//}
//
//public void drawFishRight(GraphicsContext gc) {
//    // Set the fill color for the fish's body
//    gc.setFill(Color.MEDIUMSLATEBLUE);
//
//    // Draw the fish's body as an oval, facing right
//    gc.fillOval(getX(), getY(), getWidth(), getHeight());
//
//    // Set the fill color for the fish's tail
//    gc.setFill(Color.RED);
//
//    // Draw the fish's tail as a triangle, facing right
//    double[] tailX = {getX(), getX() - getWidth() * 0.2, getX()};
//    double[] tailY = {getY() + getHeight() / 2, getY() + getHeight() * 0.8, getY() + getHeight()};
//    gc.fillPolygon(tailX, tailY, 3);
//
//    // Set the fill color for the fish's eye
//    gc.setFill(Color.BLACK);
//
//    // Draw the fish's eye as a circle, positioned on the left side of the body
//    gc.fillOval(getX() + getWidth() / 1.5, getY() + getHeight() / 3, getWidth() / 6, getHeight() / 7);
//}
//
//
//public void drawFishLeft(GraphicsContext gc) {
//    // Set the fill color for the fish's body
//    gc.setFill(Color.MEDIUMORCHID);
//
//    // Draw the fish's body as an oval, facing left
//    gc.fillOval(getX(), getY(), getWidth(), getHeight());
//
//    // Set the fill color for the fish's tail
//    gc.setFill(Color.RED);
//
//    // Draw the fish's tail as a triangle, facing left
//    double[] tailX = {getX() + getWidth(), getX() + getWidth() * 1.2, getX() + getWidth()};
//    double[] tailY = {getY() + getHeight() / 2, getY() + getHeight() * 0.8, getY() + getHeight()};
//    gc.fillPolygon(tailX, tailY, 3);
//
//    // Set the fill color for the fish's eye
//    gc.setFill(Color.BLACK);
//
//    // Draw the fish's eye as a circle, positioned on the right side of the body
//    gc.fillOval(getX() + getWidth() / 4.5, getY() + getHeight() / 3, getWidth() / 6, getHeight() / 7);
//}




