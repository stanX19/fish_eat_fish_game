package com.deepseadevs.fisheatfish;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class HistoryPage extends BasePage {
    public HistoryPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    // TODO:
    //  complete this page
    //  use sessionManager.getGameHistory() to retrieve game history
    //  display start time, level, fish eaten, score
    protected Scene createScene() {
        GridPane gridPane = new GridPane();


        return new Scene(gridPane);
    }
}
