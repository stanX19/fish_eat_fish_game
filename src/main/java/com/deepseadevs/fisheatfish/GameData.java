package com.deepseadevs.fisheatfish;

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
    // different from end time, example I can pause
    // the game and continue tomorrow and gameDuration is still 2 minutes
    // but when I complete the game, the end time will be two days from start time

    public GameData() {
        this(0, 1, 0, 0);
    }

    public GameData(long score, int level, int fishEaten, int size) {
        this(score, level, fishEaten, size, false, Instant.now(), Instant.now(), Duration.ZERO);
    }

    public GameData(long score, int level, int fishEaten, int size, boolean ended, Instant startTime, Instant endTime, Duration gameDuration) {
        this.score = score;
        this.level = level;
        this.fishEaten = fishEaten;
        this.size = size;
        this.ended = ended;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameDuration = gameDuration;
    }

    public void updateDuration(double deltaTime) {
        // seconds to milliseconds
        Duration additionalTime = Duration.ofMillis((long) (deltaTime * 1000));
        gameDuration = gameDuration.plus(additionalTime);
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

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
