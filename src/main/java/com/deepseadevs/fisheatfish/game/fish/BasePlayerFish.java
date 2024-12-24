package com.deepseadevs.fisheatfish.game.fish;

public class BasePlayerFish extends BaseFish {
    public BasePlayerFish() {
        super();
        setWeight(999);  // Just an arbitrary large number to give priority
    }

    @Override
    public double getMaxSpeed() {
        return getWidth() * 3;
    }
}
