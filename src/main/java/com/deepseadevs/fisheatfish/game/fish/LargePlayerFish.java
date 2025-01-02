package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

public class LargePlayerFish extends BasePlayerFish {
    public LargePlayerFish(){
        super();
        leftFishAnimation = new Animation("file:src/assets/sprites/pacmanleft.png", 0.1, new Bound(17,24,101,106));
        rightFishAnimation = new Animation("file:src/assets/sprites/pacmanright.png", 0.1, new Bound(27,24,111,106));
    }
}
