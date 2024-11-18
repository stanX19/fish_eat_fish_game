package com.deepseadevs.fisheatfish;

public class SessionManager {
    private UserData session;

    SessionManager() {
        this.session = null;
    }

    public void setUser(String username) {
        this.session = DatabaseManager.getInstance().getUserData(username);
    }

    public String getUsername() {
        if (this.session == null)
            return "Guest";
        return this.session.username;
    }

    public long getHighScore() {
        if (this.session == null)
            return 0;
        return this.session.highScore;
    }

    public void clearSession() {
        this.session = null;
    }

    public boolean isLoggedIn() {
        return session != null;
    }
}
