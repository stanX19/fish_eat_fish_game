package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class GiantPlayerFish extends BasePlayerFish {
    public GiantPlayerFish(){
        super();
        leftFishAnimation = new Animation("file:src/assets/sprites/rageleft.png", 0.1, new Bound(7,16,57,55));
        rightFishAnimation = new Animation("file:src/assets/sprites/rageright.png", 0.1, new Bound(7,16,57,55));
    }
}
