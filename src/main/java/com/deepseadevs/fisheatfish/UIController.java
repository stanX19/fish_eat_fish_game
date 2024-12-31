package com.deepseadevs.fisheatfish;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class UIController {
    private final Stage stage;
    private final SessionManager sessionManager;
    private final Stack<Scene> scenes = new Stack<>();

    public UIController(Stage stage, SessionManager sessionManager) {
        this.stage = stage;
        this.sessionManager = sessionManager;
    }

    public void showPage(BasePage page) {
        if (page instanceof MainMenuPage)
            scenes.clear();
        scenes.push(stage.getScene());
        showScene(page.getScene());
    }

    private void showScene(Scene scene) {
        boolean isMaximised = stage.isMaximized();
        stage.setMaximized(false);
        stage.setScene(scene);
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

    public void gotoPreviousPage() {
        if (!scenes.isEmpty())
            showScene(scenes.pop());
        else
            System.err.println("No page to go back to.");
    }

    public void logout() {
        sessionManager.clearSession();
        gotoLogin();
    }
}
