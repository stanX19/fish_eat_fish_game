package com.deepseadevs.fisheatfish.database;
import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.ArrayList;
import java.util.List;


public class UserData {
    private String userID;
    private String displayName;
    private String password;
    private long highScore;
    private List<GameData> history;
    private FishTypes fishType;

    UserData(String userID, String displayName, String password) {
        this(userID, displayName, password, 0);
    }

    UserData(String userID, String displayName, String password, long highScore) {
        this(userID, displayName, password, highScore, new ArrayList<>());
    }

    UserData(String userID, String displayName, String password, long highScore, ArrayList<GameData> history) {
        this(userID, displayName, password, highScore, history, FishTypes.PLAYER_SMALL);
    }

    UserData(String userID, String displayName, String password, long highScore, ArrayList<GameData> history, FishTypes fishType) {
        this.userID = userID;
        this.displayName = displayName;
        this.password = password;
        this.highScore = highScore;
        this.history = history;
        this.fishType = fishType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }

    public List<GameData> getHistory() {
        return history;
    }

    public void setHistory(List<GameData> history) {
        this.history = history;
    }

    public void addGameData(GameData gameData) {
        history.add(gameData);
    }

    public String getUserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    public FishTypes getFishType() {
        return fishType;
    }

    public void setFishType(FishTypes fishType) {
        this.fishType = fishType;
    }
}
