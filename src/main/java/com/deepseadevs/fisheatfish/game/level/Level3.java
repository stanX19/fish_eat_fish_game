package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;

import java.util.List;

public class Level3 extends Level {
    // TODO:
    //  overwrite getNewProgress to design different
    //  progress criteria for different level

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.LARGE, FishTypes.MEDIUM);
    }
}
