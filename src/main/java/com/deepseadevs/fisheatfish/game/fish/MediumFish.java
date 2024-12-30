package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class MediumFish extends BaseFish {
    public MediumFish() {
        super(100, 60, 40);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/greenleft.png", 0.3, new Bound(4,6,32,34));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/greenright.png", 0.3, new Bound(18,6,48,34));
    }
}
