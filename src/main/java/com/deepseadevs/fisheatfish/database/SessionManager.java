package com.deepseadevs.fisheatfish.database;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.GameData;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private UserData currentUser;

    public SessionManager() {
        this.currentUser = null;
    }

    public void setUser(String userID) {
        this.currentUser = DatabaseManager.getInstance().getUserData(userID);
    }

    /**
     * @deprecated This method is deprecated because username separated
     * into userID and displayedName. Use {@link #getDisplayName()} instead.
     */
    @Deprecated
    public String getUsername() {
        return getDisplayName();
    }

    public String getDisplayName() {
        if (this.currentUser == null)
            return "Guest";
        return this.currentUser.getDisplayName();
    }

    public String getUserID() {
        if (this.currentUser == null)
            return null;
        return this.currentUser.getUserID();
    }

    public long getHighScore() {
        if (this.currentUser == null)
            return 0;
        return this.currentUser.getHighScore();
    }

    public FishTypes getUserFishType() {
        return this.currentUser.getFishType();
    }

    public void setUserFishType(FishTypes fishType) {
        this.currentUser.setFishType(fishType);
        commit();
    }

    public void updateHighScore(long score) {
        if (score > currentUser.getHighScore())
            currentUser.setHighScore(score);
    }

    public void clearSession() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean hasOngoingGame() {
        return !getPreviousGameData().isEnded();
    }

    public GameData getPreviousGameData() {
        ArrayList<GameData> history = (ArrayList<GameData>)currentUser.getHistory();
        if (history.isEmpty()) {
            GameData dummy = new GameData();
            dummy.setEnded(true);
            return dummy;
        }
        return history.get(history.size() - 1);
    }

    public List<GameData> getGameHistory() {
        return this.currentUser.getHistory();
    }

    public GameData createNewGameData() {
        GameData gameData = new GameData();
        if (currentUser == null)
            return gameData;
        currentUser.addGameData(gameData);
        return gameData;
    }

    public void commit() {
        if (currentUser != null)
            DatabaseManager.getInstance().updateUserData(currentUser);
        else
            System.out.println("Warning: attempting to commit with null session, skipped");
    }

}
