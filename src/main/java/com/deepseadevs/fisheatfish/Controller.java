package com.deepseadevs.fisheatfish;

import javafx.stage.Stage;

public class Controller {
    private final Stage stage;
    private final App app;
    private LoginPage loginPage;

    public Controller(Stage stage, App app) {
        this.stage = stage;
        this.app = app;
        this.loginPage = new LoginPage(new LoginHandler(app));
    }

    public void showLoginPage() {
        loginPage.display(stage);
    }

    public void showGamePage() {
        System.out.println("Navigating to Game Page...");
        // Implement actual Game Page transition here
    }
}
