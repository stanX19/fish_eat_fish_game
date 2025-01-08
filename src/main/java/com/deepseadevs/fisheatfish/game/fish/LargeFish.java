package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class LargeFish extends BaseFish {
    public LargeFish() {
        super(200, 120, 80);
        leftFishAnimation = new Animation("file:src/assets/sprites/salmonleft.png", 0.75, new Bound(1,11,52,31));
        rightFishAnimation = new Animation("file:src/assets/sprites/salmonright.png", 0.75, new Bound(12,11,63,31));
    }
}
