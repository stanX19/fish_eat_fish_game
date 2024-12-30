package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Bound;
import com.deepseadevs.fisheatfish.game.Animation;

public class MediumPlayerFish extends BasePlayerFish {
    public MediumPlayerFish(){
        super();
        leftFishAnimation = new Animation("file:src/main/assets/sprites/spikeleft.png", 0.5, new Bound(16,30,88,90));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/spikeright.png", 0.5, new Bound(42,30,112,90));
    }
}
