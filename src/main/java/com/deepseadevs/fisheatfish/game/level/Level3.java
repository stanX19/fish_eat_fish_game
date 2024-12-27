package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.List;

public class Level3 extends Level {
    // TODO:
    //  overwrite getNewProgress to design different
    //  progress criteria for different level
    protected double calculateProgress(GameData gameData) {
        return gameData.getLevelFishEaten() / 32.0;
    }

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.MEDIUM, FishTypes.MEDIUM, FishTypes.LARGE);
    }
}
