package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;
import javafx.scene.canvas.GraphicsContext;

public class SmallPlayerFish extends BasePlayerFish {
    public SmallPlayerFish() {
        super();
        leftFishAnimation = new Animation("file:src/main/assets/sprites/spikeleft.png", 0.5, new Bound(22,24,118,90));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/spikeright.png", 0.5, new Bound(22,24,118,90));
    }
}