package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;
import com.deepseadevs.fisheatfish.game.PlayerHandler;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelHandler {
    ArrayList<Level> levels;
    GameData gameData;
    PlayerHandler playerHandler;

    public LevelHandler(GameData gameData, PlayerHandler playerHandler) {
        this(gameData, playerHandler, new Level1(), new Level2(), new Level3(), new Level4());
    }

    public LevelHandler(GameData gameData, PlayerHandler playerHandler, Level... levels) {
        this.levels = new ArrayList<>(Arrays.asList(levels));
        this.gameData = gameData;
        this.playerHandler = playerHandler;
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
        gameData.setLevelDuration(Duration.ZERO);
        playerHandler.resetFishEaten();
        getCurrentLevel().updateLevelProgress(gameData);
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
