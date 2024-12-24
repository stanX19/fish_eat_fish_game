package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends Level {
    // TODO:
    //  overwrite getNewProgress to design different
    //  progress criteria for different level
    protected double calculateProgress(GameData gameData) {
        return gameData.getLevelFishEaten() / 30.0;
    }

    @Override
    public int getMaxFishCount() {
        return 12;
    }

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.SMALL);
    }
}
