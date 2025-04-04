package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import com.deepseadevs.fisheatfish.game.Bound;
import javafx.scene.canvas.GraphicsContext;

public class SmallPlayerFish extends BasePlayerFish {
    public SmallPlayerFish() {
        super();
        leftFishAnimation = new Animation("file:src/assets/sprites/orenleft.png", 0.75, new Bound(0,24,123,104));
        rightFishAnimation = new Animation("file:src/assets/sprites/orenright.png", 0.75, new Bound(5,24,128,104));
    }
}