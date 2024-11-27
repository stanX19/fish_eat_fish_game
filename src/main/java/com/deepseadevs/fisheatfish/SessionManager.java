package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;

public class SessionManager {
    private UserData session;

    SessionManager() {
        this.session = null;
    }

    public void setUser(String userID) {
        this.session = DatabaseManager.getInstance().getUserData(userID);
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
        if (this.session == null)
            return "Guest";
        return this.session.getDisplayName();
    }

    public String getUserID() {
        if (this.session == null)
            return null;
        return this.session.getUserID();
    }

    public long getHighScore() {
        if (this.session == null)
            return 0;
        return this.session.getHighScore();
    }

    public void clearSession() {
        this.session = null;
    }

    public boolean isLoggedIn() {
        return session != null;
    }

    public boolean hasOngoingGame() {
        // TODO:
        //   search in session.history for most recent game
        //   if its paused, return true
        return false;
    }

    public GameData getPreviousGameData() {
        // TODO:
        //   search in session.history for most recent games
        //   return the most recent game if its paused
        //   use case: game page -> continue playing -> ...
        return new GameData();
    }
}
