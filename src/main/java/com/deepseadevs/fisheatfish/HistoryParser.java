package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HistoryParser {
    ArrayList<GameData> history;

    public HistoryParser(ArrayList<GameData> history) {
        this.history = history;
    }

    public static HistoryParser of(List<GameData> history) {
        return new HistoryParser(new ArrayList<>(history));
    }

    public long getHighestScore() {
        long highestScore = 0;
        for (GameData data: history) {
            highestScore = Math.max(highestScore, data.getScore());
        }
        return highestScore;
    }

    public int getHighestLevel() {
        int highestLevel = 0;
        for (GameData data: history) {
            highestLevel = Math.max(highestLevel, data.getLevel());
        }
        return highestLevel;
    }

    public int getMaxSize() {
        int maxSize = 0;
        for (GameData data: history) {
            maxSize = Math.max(maxSize, data.getSize());
        }
        return maxSize;
    }

    public int getTotalFishEaten() {
        int totalFishEaten = 0;
        for (GameData data: history) {
            totalFishEaten += data.getFishEaten();
        }
        return totalFishEaten;
    }

    public int getMaxFishEaten() {
        int maxFishEaten = 0;
        for (GameData data: history) {
            maxFishEaten += Math.max(maxFishEaten, data.getFishEaten());
        }
        return maxFishEaten;
    }

    public int getTotalGames() {
        return history.size();
    }

    public Duration getLongestDuration() {
        Duration longest = Duration.ZERO;
        for (GameData data : history)
            if (longest.compareTo(data.getGameDuration()) < 0)
                longest = data.getGameDuration();
        return longest;
    }

    public Duration getTotalDuration() {
        Duration total = Duration.ZERO;
        for (GameData data : history)
            total = total.plus(data.getGameDuration());
        return total;
    }
}
