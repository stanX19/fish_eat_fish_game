package com.deepseadevs.fisheatfish.game.level;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;
import com.deepseadevs.fisheatfish.game.PlayerHandler;

import java.time.Duration;
import java.util.*;


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
        getCurrentLevel().updateLevelProgress(gameData);
        return getFishTypes();
    }

    public List<FishTypes> getFishTypes() {
        ArrayList<FishTypes> currentFish = new ArrayList<>(getCurrentLevel().getFishTypes());
        if (gameData.getProgress() >= 0.5) {
            currentFish.addAll(new HashSet<>((getNextLevel().getFishTypes())));
        }
        return currentFish;
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
        return getSpecifiedLevel(gameData.getLevel());
    }

    public Level getNextLevel() {
        return getSpecifiedLevel(gameData.getLevel() + 1);
    }

    public Level getSpecifiedLevel(int level) {
        if (this.levels.isEmpty())
            throw new IllegalArgumentException("No level stored in LevelHandler");
        else if (level > levels.size())
            return levels.get(levels.size() - 1); // changed getLast() to levels.size() - 1
        else
            return levels.get(level - 1);
    }
}
