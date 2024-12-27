package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class LargeFish extends BaseFish {
    public LargeFish() {
        super(200, 120, 80);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/pacmanleft.png", 0.1, new Bound(1,1,103,98));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/pacmanright.png", 0.1, new Bound(1,1,103,98));
    }
}
