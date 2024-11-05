package com.deepseadevs.fisheatfish;

import javafx.stage.Stage;

public class UIController {
    private Stage stage;
    private SessionManager sessionManager;

    public UIController(Stage stage, SessionManager sessionManager) {
        this.stage = stage;
        this.sessionManager = sessionManager;
    }

    public void gotoLogin() {
        LoginPage loginPage = new LoginPage(this, sessionManager);
        loginPage.show(stage);
    }

    public void gotoMainMenu() {
        MainMenuPage mainMenuPage = new MainMenuPage(this, sessionManager);
        mainMenuPage.show(stage);
    }

    public void logout() {
        sessionManager.clearSession();
        gotoLogin();
    }
}
