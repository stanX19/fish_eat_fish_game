package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class MediumFish extends BaseFish {
    public MediumFish() {
        super(100, 60, 40);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/greenleft.png", 0.1, new Bound(1,1,40,35));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/greenright.png", 0.1, new Bound(1,1,40,35));
    }
}
