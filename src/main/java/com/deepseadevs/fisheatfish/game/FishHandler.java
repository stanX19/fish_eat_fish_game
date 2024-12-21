package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishHandler {
    Bound bound;
    private final int xRemoveBuffer;
    private final List<BaseFish> allFish;

    public FishHandler(Bound bound) {
        this.allFish = new ArrayList<>();
        this.bound = bound;
        this.xRemoveBuffer = 100;
    }

    public void addFish(BaseFish fish) {
        allFish.add(fish);
    }

    public void addFish(BaseFish... fishes) {
        allFish.addAll(Arrays.asList(fishes));
    }

    public boolean containsFish(BaseFish fish) {
        return allFish.contains(fish);
    }

    public void removeFish(BaseFish fish) {
        allFish.remove(fish);
    }

    public void clearFish() {
        allFish.clear();
    }

    public void renderAll(GraphicsContext gc) {
        for (BaseFish fish: allFish) {
            fish.render(gc);
        }
    }

    public void updateAll(double deltaTime) {
        for (int i = 0; i < allFish.size(); i++) {
            BaseFish fish = allFish.get(i);
            boundFishMovements(fish);
            removeIfOutOfBound(fish);
            fish.update(deltaTime);
        }
    }

    private void removeIfOutOfBound(BaseFish fish) {
        if (fish.getX() > bound.maxX + xRemoveBuffer && fish.getXv() > 0)
            removeFish(fish);
        if (fish.getX() + fish.getWidth() < bound.minX - xRemoveBuffer && fish.getXv() < 0)
            removeFish(fish);
    }

    private void boundFishMovements(BaseFish fish) {
        if (fish.getY() + fish.getHeight() > bound.maxY && fish.getYv() > 0)
            fish.setYv(-fish.getYv());
        if (fish.getY() < bound.minY && fish.getYv() < 0)
            fish.setYv(-fish.getYv());
    }

    public void collideAll() {
        for (int i = 0; i < allFish.size(); i++) {
            for (int j = i + 1; j < allFish.size(); j++) {
                BaseFish fish1 = allFish.get(i);
                BaseFish fish2 = allFish.get(j);
                if (fish1.collidesWith(fish2)) {
                    handleFishFishCollision(fish1, fish2);
                }
            }
        }
    }

    private void handleFishFishCollision(BaseFish fish1, BaseFish fish2) {
        if (fish1.isBiggerThan(fish2)) {
            handleFishFishCollision(fish2, fish1);
        } else {
            allFish.remove(fish1);
            fish2.setArea(fish2.getArea() + fish1.getArea() * 0.1);
            fish2.incrementFishEaten();
        }
    }

    public int getFishCount() {
        return allFish.size();
    }

    public int getFishCountInBound() {
        return getFishCountInBound(bound);
    }

    public int getFishCountInBound(Bound bound) {
        int count = 0;
        for (BaseFish fish: allFish) {
            if (bound.contains(fish.getX(), fish.getY()))
                count++;
        }
        return count;
    }
}
