package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Spawner {
    static final int BUFFER = 600;
    private double bufferRatio;
    private final Bound bound;
    private final Random random;

    public Spawner(Bound bound) {
        this.bound = bound;
        this.random = new Random();
        this.bufferRatio = 0;
    }

    public void setBufferRatio(double bufferRatio) {
        this.bufferRatio = bufferRatio;
    }

    private BaseFish fishTypeToFish(FishTypes fishType) {
        switch (fishType) {
            case SMALL:
                return new SmallFish();
            case MEDIUM:
                return new MediumFish();
            case LARGE:
                return new LargeFish();
            case GIANT:
                return new GiantFish();
            case PLAYER_SMALL:
                return new SmallPlayerFish();
            case PLAYER_MEDIUM:
                return new MediumPlayerFish();
            case PLAYER_LARGE:
                return new LargePlayerFish();
            case PLAYER_GIANT:
                return new GiantPlayerFish();
            default:
                throw new IllegalArgumentException("Unhandled fish type: " + fishType);
        }
    }

    public BaseFish spawnRandomFish(FishTypes... fishTypes) {
        return spawnRandomFish(new ArrayList<>(Arrays.asList(fishTypes)));
    }

    public BaseFish spawnRandomFish(List<FishTypes> fishTypes) {
        return spawnRandomFish(new ArrayList<>(fishTypes));
    }

    public BaseFish spawnRandomFish(ArrayList<FishTypes> fishTypesList) {
        if (fishTypesList == null || fishTypesList.isEmpty()) {
            throw new IllegalArgumentException("No fish types provided");
        }
        int idx = random.nextInt(fishTypesList.size());
        FishTypes selectedFishType = fishTypesList.get(idx);
        return spawnFish(selectedFishType);
    }

    public BaseFish spawnFish(FishTypes fishType) {
        BaseFish newFish = fishTypeToFish(fishType);
        return configureNewFish(newFish);
    }

    public BaseFish spawnFishAtCentre(FishTypes fishType) {
        return spawnFishAt(fishType, bound.getMidX(), bound.getMidY());
    }

    public BaseFish spawnFishAt(FishTypes fishType, double x, double y) {
        BaseFish newFish = fishTypeToFish(fishType);
        newFish.setX(x);
        newFish.setY(y);
        return newFish;
    }

    private BaseFish configureNewFish(BaseFish newFish) {
        double buffer = bufferRatio * BUFFER + 1;
        if (Math.random() > 0.5) {// left side
            newFish.setX(random.nextDouble(
                    bound.minX - 2 * buffer - newFish.getWidth(),
                    bound.minX - buffer - newFish.getWidth()
            ));
        } else { // right side
            newFish.setX(random.nextDouble(
                    bound.maxX + buffer,
                    bound.maxX + 2 * buffer
            ));
        }
        newFish.setY(random.nextDouble(bound.minY, bound.maxY));
        newFish.setXv(random.nextInt(50, 200));
        newFish.setYv(random.nextInt(0, 20));
        newFish.setSpeed(newFish.getMaxSpeed());
        return newFish;
    }
}
