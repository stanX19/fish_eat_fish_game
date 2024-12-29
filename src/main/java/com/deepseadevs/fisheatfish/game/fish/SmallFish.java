package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class SmallFish extends BaseFish {
    public SmallFish() {
        super(50, 30, 20);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/redleft.png",1, new Bound(17,20,52,41));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/redright.png",1, new Bound(14,20,47,41));
    }
}
