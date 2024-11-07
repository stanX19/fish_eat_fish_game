package com.deepseadevs.fisheatfish;

class Session {
    public String username;

    Session(String username) {
        this.username = username;
    }
}

public class SessionManager {
    private Session session;

    SessionManager() {
        this.session = null;
    }

    public void setUser(String username) {
        this.session = new Session(username);
    }

    public String getUsername() {
        return this.session.username;
    }

    public void clearSession() {
        this.session = null;
    }

    public boolean isLoggedIn() {
        return session != null;
    }
}
