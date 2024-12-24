package com.deepseadevs.fisheatfish.game.fish;
import com.deepseadevs.fisheatfish.game.Animation;
import javafx.scene.canvas.GraphicsContext;

public class BaseFish extends GameObject {
    protected boolean facingRight;
    private int fishEaten;
    private int weight;
    protected Animation leftFishAnimation;
    protected Animation rightFishAnimation;

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
        this.leftFishAnimation = new Animation("file:src/main/assets/sprites/redleftsprite.png");
        this.rightFishAnimation = new Animation("file:src/main/assets/sprites/redrightsprite.png");
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
        callAnimationRender(gc, rightFishAnimation);
    }

    public void drawFishLeft(GraphicsContext gc) {
        callAnimationRender(gc, leftFishAnimation);
    }

    private void callAnimationRender(GraphicsContext gc, Animation animation) {
        animation.render(gc, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        leftFishAnimation.update(deltaTime);
        rightFishAnimation.update(deltaTime);
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
