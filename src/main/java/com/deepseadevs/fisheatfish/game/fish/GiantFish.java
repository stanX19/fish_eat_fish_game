package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class GiantFish extends BaseFish {
    public GiantFish() {
        super(300, 240, 160);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/sharkleft.png", 0.75, new Bound(14,30,57,45));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/sharkright.png", 0.75, new Bound(15,30,59,45));
    }
}
