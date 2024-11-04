package com.deepseadevs.fisheatfish;

public class LoginHandler {
    private final App app;

    public LoginHandler(App app) {
        this.app = app;
    }

    public boolean login(String username, String password) {
        boolean isValid = app.getDataHandler().verifyCredentials(username, password);
        if (isValid) {
            app.getCurrentPlayer().setUsername(username);
            app.getController().showGamePage();  // Navigate to game page
            return true;
        } else {
            return false;
        }
    }
}
