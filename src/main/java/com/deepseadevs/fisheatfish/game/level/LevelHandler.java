package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelHandler {
    ArrayList<Level> levels;
    GameData gameData;

    public LevelHandler(GameData gameData) {
        this(gameData, new Level1(), new Level2(), new Level3(), new Level4());
    }

    public LevelHandler(GameData gameData, Level... levels) {
        this.levels = new ArrayList<>(Arrays.asList(levels));
        this.gameData = gameData;
    }

    public List<FishTypes> updateAndGetFishTypes() {
        Level level = getCurrentLevel();
        level.updateLevelProgress(gameData);
        return level.getFishTypes(gameData.getProgress());
    }

    public List<FishTypes> getFishTypes() {
        return getCurrentLevel().getFishTypes(gameData.getProgress());
    }

    public int getMaxFishCount() {
        return getCurrentLevel().getMaxFishCount();
    }

    public void incrementLevel() {
        gameData.setLevel(gameData.getLevel() + 1);
    }

    public int getTotalLevels() {
        return levels.size();
    }

    public void updateProgress() {
        getCurrentLevel().updateLevelProgress(gameData);
    }

    public Level getCurrentLevel() {
        if (this.levels.isEmpty())
            throw new IllegalArgumentException("No level stored in LevelHandler");
        else if (gameData.getLevel() > levels.size())
            return levels.getLast();
        else
            return levels.get(gameData.getLevel() - 1);
    }
}
