package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;

// TODO:
//  ADD design and customization, override methods for custom behaviour
public class LargeFish extends BaseFish {
    public LargeFish() {
        super(200, 120, 80);
        leftFishAnimation = new Animation("file:src/main/assets/sprites/pacmanleft.png", 0.1, new Bound(8,14,111,112));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/pacmanright.png", 0.1, new Bound(8,14,111,112));
    }
}
