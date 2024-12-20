package com.deepseadevs.fisheatfish.game.fish;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class GiantPlayerFish extends BasePlayerFish {
    @Override
    public double getMaxSpeed() {
        return getArea() / 10;
    }
}
