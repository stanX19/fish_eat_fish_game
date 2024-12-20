package com.deepseadevs.fisheatfish.game.fish;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class BaseFish extends GameObject {
    protected boolean facingRight;
    private int fishEaten;
    private int weight;

    public BaseFish() {
        this(100, 60, 40);
    }

    public BaseFish(double maxSpeed, double width, double height) {
        this(maxSpeed, width, height, 0, 0, 0, 0);
    }

    public BaseFish(double maxSpeed, double width, double height, int fishEaten, int weight) {
        this(maxSpeed, width, height, fishEaten, weight, 0, 0);
    }

    public BaseFish(double maxSpeed, double width, double height, int fishEaten, int weight, double x, double y) {
        super(x, y, maxSpeed, width, height);
        this.facingRight = true;
        this.fishEaten = fishEaten;
        this.weight = weight;
    }

    public boolean isBiggerThan(BaseFish other) {
        if (this.getArea() == other.getArea())
            return this.getWeight() > other.getWeight();
        return this.getArea() > other.getArea();
    }

    public void render(GraphicsContext gc) {
        if(getXv() < 0){
            facingRight = false;
        }
        else if(getXv() > 0){
            facingRight = true;
        }

        if (facingRight) {
            drawFishRight(gc);
        } else {
            drawFishLeft(gc);
        }
    }

    public void drawFishRight(GraphicsContext gc) {
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
        gc.fillOval(getX() + getWidth() / 1.5, getY() + getHeight() / 3, getWidth() / 6, getHeight() / 7);
    }

    public void drawFishLeft(GraphicsContext gc) {
        // Set the fill color for the fish's body
        gc.setFill(Color.CORAL);

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

    public int getFishEaten() {
        return fishEaten;
    }

    public void setFishEaten(int fishEaten) {
        this.fishEaten = fishEaten;
    }

    public void incrementFishEaten() {
        fishEaten += 1;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
