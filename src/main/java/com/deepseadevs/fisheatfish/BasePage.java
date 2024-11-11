package com.deepseadevs.fisheatfish;

import javafx.scene.Scene;

public abstract class BasePage {
    protected final UIController uiController;
    protected final SessionManager sessionManager;
    protected final Scene scene;

    public BasePage(UIController uiController, SessionManager sessionManager) {
        this.uiController = uiController;
        this.sessionManager = sessionManager;
        this.scene = createScene();
    }

    public Scene getScene() {
        return this.scene;
    }

    protected abstract Scene createScene();
}

