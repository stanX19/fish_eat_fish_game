package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;
import javafx.scene.canvas.GraphicsContext;

public class SmallPlayerFish extends BasePlayerFish {
    public SmallPlayerFish() {
        super();
        leftFishAnimation = new Animation("file:src/main/assets/sprites/orangeleft.png", 0.5, new Bound(1,1,65,45));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/orangeright.png", 0.5, new Bound(1,1,65,45));
    }
}