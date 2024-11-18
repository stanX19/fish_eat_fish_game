package com.deepseadevs.fisheatfish;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;


public class BaseFish extends GameObject {
    public BaseFish(double x, double y, double maxSpeed, double width, double height) {
        super(x, y, maxSpeed, width, height);
    }

    // TODO
    //  Overwrite render method to draw a fish
    public void render(GraphicsContext gc) {
        // Set the fill color for the fish's body
        gc.setFill(Color.ORANGE);

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
        gc.fillOval(getX() + getWidth() / 1.5, getY() + getHeight() / 3, getWidth() / 7, getHeight() / 7);
    }
}
