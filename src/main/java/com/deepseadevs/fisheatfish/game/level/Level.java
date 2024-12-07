package com.deepseadevs.fisheatfish.game.level;
import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.List;

public class Level {
    public List<FishTypes> getFishTypes() {
        return List.of(FishTypes.SMALL);
    }

    public int getMaxFishCount() {
        return 10;
    }

    public final void updateLevelProgress(GameData gameData) {
        gameData.setLevelProgress(calculateProgress(gameData));
    }

    protected double calculateProgress(GameData gameData) {
        return gameData.getLevelFishEaten() / 30.0;
    }
}
