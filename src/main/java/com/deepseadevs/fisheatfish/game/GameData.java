package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.game.fish.SmallFish;

import java.time.Duration;
import java.time.Instant;

public class GameData {
    private long score;
    private int level;
    private int fishEaten;
    private int size;
    private boolean ended;
    private final Instant startTime;
    private Instant endTime;
    private Duration gameDuration;

    private int levelFishEaten;
    private Duration levelDuration;
    private double levelProgress;

    public GameData() {
        this(0, 1, 0, (int)(new SmallFish()).getArea() + 100);
    }

    public GameData(long score, int level, int fishEaten, int size) {
        this(score, level, fishEaten, size, false, Instant.now(), Instant.now(), Duration.ZERO);
    }
    public GameData(long score, int level, int fishEaten, int size, boolean ended, Instant startTime, Instant endTime, Duration gameDuration) {
        this(score, level, fishEaten, size, ended, startTime, endTime, gameDuration, 0, Duration.ZERO, 0);
    }
    public GameData(long score, int level, int fishEaten, int size, boolean ended, Instant startTime, Instant endTime, Duration gameDuration, int levelFishEaten, Duration levelDuration, double levelProgress) {
        this.score = score;
        this.level = level;
        this.fishEaten = fishEaten;
        this.size = size;
        this.ended = ended;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameDuration = gameDuration;
        this.levelFishEaten = levelFishEaten;
        this.levelDuration = levelDuration;
        this.levelProgress = levelProgress;
    }

    public void updateDuration(double deltaTime) {
        // seconds to milliseconds
        Duration additionalTime = Duration.ofMillis((long) (deltaTime * 1000));
        gameDuration = gameDuration.plus(additionalTime);
        levelDuration = levelDuration.plus(additionalTime);
        endTime = Instant.now();
    }

    public int getFishEaten() {
        return fishEaten;
    }

    public void setFishEaten(int fishEaten) {
        this.fishEaten = fishEaten;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Duration getGameDuration() {
        return gameDuration;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public double getProgress() {
        return levelProgress;
    }

    public void setLevelProgress(double levelProgress) {
        this.levelProgress = levelProgress;
    }

    public int getLevelFishEaten() {
        return levelFishEaten;
    }

    public void setLevelFishEaten(int levelFishEaten) {
        this.levelFishEaten = levelFishEaten;
    }

    public Duration getLevelDuration() {
        return levelDuration;
    }

    public void setLevelDuration(Duration levelDuration) {
        this.levelDuration = levelDuration;
    }
}
