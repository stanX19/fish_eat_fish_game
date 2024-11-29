package com.deepseadevs.fisheatfish.game.fish;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class SmallPlayerFish extends BaseFish {
    @Override
    public double getMaxSpeed() {
        return getArea() / 10;
    }
}
