package com.deepseadevs.fisheatfish.game.fish;

import com.deepseadevs.fisheatfish.game.Animation;
import javafx.scene.canvas.GraphicsContext;

public class SmallPlayerFish extends BasePlayerFish {
    protected Animation leftFishAnimation;
    protected Animation rightFishAnimation;

    // TODO:
    //  put the image files in src/main/assets/sprites
    //  put all the animation code into BaseFish since all fish will use the same logic
    //  the only difference will be animation used, we can change them after super call
    //  example:
    //    public MediumPlayerFish() {
    //      super();
    //      leftFishAnimation = new Animation("file:src/main/java/sprites/redleftsprite.png");
    //      rightFishAnimation = new Animation("file:src/main/java/sprites/redrightsprite.png");
    //    }
    public SmallPlayerFish() {
        leftFishAnimation = new Animation("file:src/main/java/sprites/orangeleft.png");
        rightFishAnimation = new Animation("file:src/main/java/sprites/orangeright.png");
    }

    @Override
    public void drawFishRight(GraphicsContext gc) {
        rightFishAnimation.render(gc, this.getX(), this.getY());
    }

    @Override
    public void drawFishLeft(GraphicsContext gc) {
        leftFishAnimation.render(gc, this.getX(), this.getY());
    }


    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        leftFishAnimation.update(deltaTime);
        rightFishAnimation.update(deltaTime);
    }
}