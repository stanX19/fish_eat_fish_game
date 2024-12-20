package com.deepseadevs.fisheatfish.game.fish;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class SmallPlayerFish extends BaseFish {
    public SmallPlayerFish() {
        super();
        setArea(700);
    }
    public void drawFishRight(GraphicsContext gc) {
        // Set the fill color for the fish's body
        gc.setFill(Color.MEDIUMSLATEBLUE);

        // Draw the fish's body as an oval, facing right
        gc.fillOval(getX(), getY(), getWidth(), getHeight());

        // Set the fill color for the fish's tail
        gc.setFill(Color.RED);

        // Draw the fish's tail as a triangle, facing right
        double[] tailX = {getX(), getX() - getWidth() * 0.2, getX()};
        double[] tailY = {getY() + getHeight() / 2, getY() + getHeight() * 0.8, getY() + getHeight()};
        gc.fillPolygon(tailX, tailY, 3);

        // Set the fill color for the fish's eye
        gc.setFill(Color.BLACK);

        // Draw the fish's eye as a circle, positioned on the left side of the body
        gc.fillOval(getX() + getWidth() / 1.5, getY() + getHeight() / 3, getWidth() / 6, getHeight() / 7);
    }


    public void drawFishLeft(GraphicsContext gc) {
        // Set the fill color for the fish's body
        gc.setFill(Color.MEDIUMORCHID);

        // Draw the fish's body as an oval, facing left
        gc.fillOval(getX(), getY(), getWidth(), getHeight());

        // Set the fill color for the fish's tail
        gc.setFill(Color.RED);

        // Draw the fish's tail as a triangle, facing left
        double[] tailX = {getX() + getWidth(), getX() + getWidth() * 1.2, getX() + getWidth()};
        double[] tailY = {getY() + getHeight() / 2, getY() + getHeight() * 0.8, getY() + getHeight()};
        gc.fillPolygon(tailX, tailY, 3);

        // Set the fill color for the fish's eye
        gc.setFill(Color.BLACK);

        // Draw the fish's eye as a circle, positioned on the right side of the body
        gc.fillOval(getX() + getWidth() / 4.5, getY() + getHeight() / 3, getWidth() / 6, getHeight() / 7);
    }

    @Override
    public double getMaxSpeed() {
        return getWidth() * 3;
    }
}
