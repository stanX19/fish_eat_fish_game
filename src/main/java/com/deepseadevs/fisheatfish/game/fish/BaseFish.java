package com.deepseadevs.fisheatfish.game.fish;
import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;
import com.deepseadevs.fisheatfish.game.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BaseFish extends GameObject {
    protected boolean facingRight;
    private int fishEaten;
    private int weight;
    private long score;
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
        this.score = 0;
        this.leftFishAnimation = new Animation("file:src/assets/sprites/redleft.png",1, new Bound(17,20,52,41));
        this.rightFishAnimation = new Animation("file:src/assets/sprites/redright.png",1, new Bound(14,20,47,41));
    }

    public boolean isBiggerThan(BaseFish other) {
        if (this.getArea() == other.getArea())
            return this.getWeight() > other.getWeight();
        return this.getArea() > other.getArea();
    }

    public void renderHitBox(GraphicsContext gc) {
        gc.setStroke(Color.LIME);
        gc.setLineWidth(2);
        gc.strokeRect(getX(), getY(), getWidth(), getHeight());
        gc.restore();
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
        double area = getArea();
        this.setWidth(rightFishAnimation.getSpriteDimension().getWidth());
        this.setHeight(rightFishAnimation.getSpriteDimension().getHeight());
        this.setArea(area);
        callAnimationRender(gc, rightFishAnimation);
    }

    public void drawFishLeft(GraphicsContext gc) {
        double area = getArea();
        this.setWidth(leftFishAnimation.getSpriteDimension().getWidth());
        this.setHeight(leftFishAnimation.getSpriteDimension().getHeight());
        this.setArea(area);
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

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void addScore(long score) {
        this.score += score;
    }
}
