package com.deepseadevs.fisheatfish;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameObject {
    private double x, y;
    private double xv, yv;
    private double maxSpeed;
    private double width, height;
    private Color color;

    public GameObject(double x, double y, double maxSpeed, double width, double height) {
        this(x, y, maxSpeed, width, height, Color.BLUE);
    }

    public GameObject(double x, double y, double maxSpeed, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.xv = 0;
        this.yv = 0;
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;
        this.color = color;
    }

    public void update(double deltaTime) {
        move(deltaTime);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillOval(x, y, width, height);
    }

    /**
     * Moves the GameObject based on its velocity and the elapsed time.
     * Speed is limited to maxSpeed.
     */
    public void move(double deltaTime) {
        double currentSpeed = getSpeed();
        if (currentSpeed > maxSpeed) {
            setSpeed(maxSpeed);
        }
        this.x += xv * deltaTime;
        this.y += yv * deltaTime;
    }

    /**
     * Checks if this GameObject collides with another GameObject.
     * Collision detection is done using axis-aligned bounding boxes (AABB).
     *
     * @param other The other GameObject to check collision with.
     * @return true if this GameObject collides with the other, false otherwise.
     */
    public boolean collidesWith(GameObject other) {
        return this.x < other.x + other.width && this.x + this.width > other.x
                && this.y < other.y + other.height && this.y + this.height > other.y;
    }

    public double getSpeed() {
        return Math.hypot(xv, yv);
    }

    public void setSpeed(double speed) {
        double originalAngle = getAngle();
        this.xv = speed * Math.cos(originalAngle);
        this.yv = speed * Math.sin(originalAngle);
        double EPSILON = 1e-6;
        if (Math.abs(this.xv) < EPSILON) {
            this.xv = 0;
        }
        if (Math.abs(this.yv) < EPSILON) {
            this.yv = 0;
        }
    }

    public double getAngle() {
        return Math.atan2(yv, xv);
    }

    public void setAngle(double angle) {
        double speed = getSpeed();
        this.xv = speed * Math.cos(angle);
        this.yv = speed * Math.sin(angle);
    }
    
    public double getArea() {
        return this.height * this.width;
    }

    public void setArea(double newArea) {
        if (newArea <= 0)
            return;
        if (this.height == 0 && this.width == 0) {
            this.height = Math.sqrt(newArea);
            this.width = Math.sqrt(newArea);
        } else if (this.height == 0) {
            this.height = newArea / this.width;
        } else if (this.width == 0) {
            this.width = newArea / this.height;
        } else {
            double scaleFactor = Math.sqrt(newArea / getArea());
            this.height *= scaleFactor;
            this.width *= scaleFactor;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXv() {
        return xv;
    }

    public void setXv(double xv) {
        this.xv = xv;
    }

    public double getYv() {
        return yv;
    }

    public void setYv(double yv) {
        this.yv = yv;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
