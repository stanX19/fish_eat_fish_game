package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends Level {
    // TODO:
    //  overwrite getNewProgress to design different
    //  progress criteria for different level
    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.SMALL);
    }
}
