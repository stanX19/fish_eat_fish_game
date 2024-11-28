package com.deepseadevs.fisheatfish.game;

import java.util.Random;

public class Bound {
    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    public Bound(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public double getMidX() {
        return minX + (maxX - minX) / 2;
    }

    public double getMidY() {
        return minY + (maxY - minY) / 2;
    }
}
