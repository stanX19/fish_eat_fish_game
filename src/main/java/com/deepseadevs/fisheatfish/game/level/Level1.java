package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends Level {
    protected double calculateProgress(GameData gameData) {
        return gameData.getScore() / 4640.0;
    }

    @Override
    public int getMaxFishCount() {
        return 12;
    }

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.SMALL);
    }
}
