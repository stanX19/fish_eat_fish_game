package com.deepseadevs.fisheatfish;

import javafx.stage.Stage;

public class UIController {
    private final Stage stage;
    private final SessionManager sessionManager;

    public UIController(Stage stage, SessionManager sessionManager) {
        this.stage = stage;
        this.sessionManager = sessionManager;
    }

    public void showPage(BasePage page) {
        boolean isMaximised = stage.isMaximized();
        stage.setMaximized(false);
        stage.setScene(page.getScene());
        stage.setMaximized(isMaximised);
    }

    public void gotoLogin() {
        LoginPage loginPage = new LoginPage(this, sessionManager);
        showPage(loginPage);
    }

    public void gotoNewAccount() {
        NewAccountPage loginPage = new NewAccountPage(this, sessionManager);
        showPage(loginPage);
    }

    public void gotoMainMenu() {
        MainMenuPage mainMenuPage = new MainMenuPage(this, sessionManager);
        showPage(mainMenuPage);
    }

    public void gotoGamePage() {
        GamePage gamePage = new GamePage(this, sessionManager);
        showPage(gamePage);
    }

    public void gotoGamePage(boolean continueGame) {
        GamePage gamePage = new GamePage(this, sessionManager, continueGame);
        showPage(gamePage);
    }

    public void gotoFishSelectionPage() {
        FishSelectionPage fishSelectionPage = new FishSelectionPage(this, sessionManager);
        showPage(fishSelectionPage);
    }

    public void gotoLeaderBoard() {
        LeaderboardPage leaderboardPage = new LeaderboardPage(this, sessionManager);
        showPage(leaderboardPage);
    }

    public void gotoHistoryPage() {
        HistoryPage historyPage = new HistoryPage(this, sessionManager);
        showPage(historyPage);
    }

    public void gotoHistoryPage(UserData userData) {
        HistoryPage historyPage = new HistoryPage(this, sessionManager, userData);
        showPage(historyPage);
    }

    public void logout() {
        sessionManager.clearSession();
        gotoLogin();
    }
}
