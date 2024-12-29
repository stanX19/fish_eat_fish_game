package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;
import javafx.scene.canvas.GraphicsContext;

public class SmallPlayerFish extends BasePlayerFish {
    public SmallPlayerFish() {
        super();
        leftFishAnimation = new Animation("file:src/main/assets/sprites/orenleft.png", 0.75, new Bound(0,12,63,50));
        rightFishAnimation = new Animation("file:src/main/assets/sprites/orenright.png", 0.75, new Bound(0,12,63,50));
    }
}