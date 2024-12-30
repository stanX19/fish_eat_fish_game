package com.deepseadevs.fisheatfish.game.level;
import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.List;

public class Level2 extends Level {
    protected double calculateProgress(GameData gameData) {
        return gameData.getScore() / 15000.0;
    }

    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.SMALL, FishTypes.MEDIUM, FishTypes.MEDIUM, FishTypes.MEDIUM);
    }
}

