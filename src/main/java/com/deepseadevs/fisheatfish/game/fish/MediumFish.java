package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class MediumFish extends BaseFish {
    public MediumFish() {
        super(100, 60, 40);
        leftFishAnimation = new Animation("file:src/assets/sprites/greenleft.png", 0.3, new Bound(4,12,32,30));
        rightFishAnimation = new Animation("file:src/assets/sprites/greenright.png", 0.3, new Bound(18,12,48,30));
    }
}
