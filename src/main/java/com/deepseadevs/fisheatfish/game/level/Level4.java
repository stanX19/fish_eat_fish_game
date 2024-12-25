package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.List;

public class Level4 extends Level {
    @Override
    protected double calculateProgress(GameData gameData) {
        return gameData.getLevelDuration().getSeconds() / 120.0;
    }

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.GIANT, FishTypes.LARGE, FishTypes.LARGE, FishTypes.MEDIUM);
    }
}
                 