package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;

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
}
