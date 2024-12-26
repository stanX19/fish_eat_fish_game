package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class MediumFish extends BaseFish {
    public MediumFish() {
        super(100, 60, 40);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/rageleft.png", 0.1, new Bound(1,1,61,58));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/rageright.png", 0.1, new Bound(1,1,61,58));
    }
}
