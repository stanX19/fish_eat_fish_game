package com.deepseadevs.fisheatfish;

import javafx.stage.Stage;

public class UIController {
    private Stage stage;
    private SessionManager sessionManager;

    public UIController(Stage stage, SessionManager sessionManager) {
        this.stage = stage;
        this.sessionManager = sessionManager;
    }

    public void showPage(AppPage page) {
        stage.setScene(page.getScene());
        stage.show();
    }

    public void gotoLogin() {
        LoginPage loginPage = new LoginPage(this, sessionManager);
        showPage(loginPage);
    }

    public void gotoMainMenu() {
        MainMenuPage mainMenuPage = new MainMenuPage(this, sessionManager);
        showPage(mainMenuPage);
    }

    public void logout() {
        sessionManager.clearSession();
        gotoLogin();
    }
}
