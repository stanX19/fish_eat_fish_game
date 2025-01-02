package com.deepseadevs.fisheatfish.pages;

import com.deepseadevs.fisheatfish.*;
import com.deepseadevs.fisheatfish.database.DatabaseManager;
import com.deepseadevs.fisheatfish.database.HistoryParser;
import com.deepseadevs.fisheatfish.database.SessionManager;
import com.deepseadevs.fisheatfish.database.UserData;
import com.deepseadevs.fisheatfish.game.GameData;
import com.deepseadevs.fisheatfish.game.Spawner;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import com.deepseadevs.fisheatfish.widgets.GameStyles;
import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
import com.deepseadevs.fisheatfish.widgets.labels.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class HistoryPage extends BasePage {
    private VBox contentBox;
    private AnimationTimer animationTimer;
    private final String BACKGROUND_COLOR = "#1a202c";
    private final String CARD_COLOR = "#222a3a";

    public HistoryPage(UIController uiController, SessionManager sessionManager) {
        this(uiController, sessionManager, DatabaseManager.getInstance().getUserData(sessionManager.getUserID()));
    }

    public HistoryPage(UIController uiController, SessionManager sessionManager, UserData userData) {
        super(uiController, sessionManager);
        createDynamicContent(userData);
    }

    protected Scene createScene() {
        // Centered VBox for content
        contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(20);
        contentBox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Wrap contentBox in a ScrollPane to allow scrolling for the entire scene
        ScrollPane root = new ScrollPane(contentBox);
        root.setFitToWidth(true);
        root.setFitToHeight(true);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        root.setPadding(new Insets(20));
        // fast scrolling
        root.getContent().setOnScroll(scrollEvent -> {
            double contentHeight = contentBox.getHeight();
            double viewportHeight = root.getViewportBounds().getHeight();
            double scrollHeight = contentHeight - viewportHeight;
            if (scrollHeight <= 0)
                return;
            double deltaY = (scrollEvent.getDeltaY() > 0? 100: -100) / scrollHeight;
            double vValue = root.getVvalue();
            root.setVvalue(vValue - deltaY);
        });


        return new Scene(root, 800, 500); // Adjust dimensions as necessary
    }

    private void createDynamicContent(UserData userData) {
        // Title label
        TitleLabel titleLabel = new TitleLabel("PROFILE");

        VBox statsLayout = createStatsLayout(userData);

        // History layout
        VBox historyLayout = createHistoryLayout(userData);

        // Add content to the box
        contentBox.getChildren().addAll(titleLabel, statsLayout, historyLayout);
    }

    private AnimationTimer getAnimationTimer(GraphicsContext gc, Canvas canvas, BaseFish currentFish) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.web(CARD_COLOR));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                currentFish.update(deltaTime);
                currentFish.setX(canvas.getWidth() / 2 - currentFish.getWidth() / 2);
                currentFish.setY(canvas.getHeight() / 2 - currentFish.getHeight() / 2);
                currentFish.render(gc);
            }
        };
        timer.start();
        return timer;
    }

    private VBox createStatsLayout(UserData userData) {
        VBox statsLayout = new VBox();
        statsLayout.setSpacing(10);
        statsLayout.setPadding(new Insets(15));
        statsLayout.setStyle("-fx-background-radius: 10; -fx-background-color: " + CARD_COLOR + ";");
        statsLayout.setMaxWidth(500);
        statsLayout.setAlignment(Pos.CENTER);

        Canvas canvas = new Canvas(200, 60);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Spawner spawner = new Spawner();
        BaseFish currentFish = spawner.spawnFish(userData.getFishType());
        currentFish.setXv(1);
        currentFish.setArea(1600);
        animationTimer = getAnimationTimer(gc, canvas, currentFish);

        VBox nameBox = new VBox();
        nameBox.setAlignment(Pos.CENTER);
        Label usernameLabel = new GeneralLabel(userData.getDisplayName(), Font.font("Arial", FontWeight.BOLD, 16), GameStyles.TEXT_COLOR);
        Label userIDLabel = new GeneralLabel(userData.getUserID(), Font.font("Arial", 12), "#888888");
        nameBox.getChildren().addAll(usernameLabel, userIDLabel);

        HistoryParser parser = HistoryParser.of(userData.getHistory());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10)); // Padding around the grid
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(20); // Vertical gap between rows

        // Add ColumnConstraints to split columns evenly
        ColumnConstraints[] col = new ColumnConstraints[3];
        for (int i = 0; i < 3; i++) {
            col[i] = new ColumnConstraints();
            col[i].setPercentWidth(100.0 / 3.0);
        }

        // Add constraints to the GridPane
        gridPane.getColumnConstraints().addAll(col);

        String[][] text = {
                { String.valueOf(userData.getHighScore()), String.valueOf(parser.getHighestLevel()), durationToString(parser.getLongestDuration()) },
                { "Highest Score", "Highest Level", "Longest Game" },
                { String.valueOf(parser.getTotalFishEaten()), String.valueOf(parser.getTotalGames()), durationToString(parser.getTotalDuration()) },
                { "Total Fish Eaten", "Total Games Played", "Total Time Played" }
        };
        Label[][] labels = new Label[4][3];
        VBox[][] vBoxes = new VBox[2][3];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i % 2 == 0)
                    labels[i][j] = new GeneralLabel(text[i][j], GameStyles.MAIN_FONT, GameStyles.TEXT_COLOR);
                else {
                    labels[i][j] = new GeneralLabel(text[i][j], Font.font("Arial", 12), "#888888");
                    vBoxes[i/2][j] = new VBox();
                    vBoxes[i/2][j].getChildren().addAll(labels[i-1][j], labels[i][j]);
                    gridPane.add(vBoxes[i/2][j], j, i/2);
                }
                setLabelProperties(labels[i][j]);
            }
        }

        // Back button
        MainButton backButton = new MainButton("Back");
        backButton.setOnAction(e -> {
            animationTimer.stop();
            uiController.gotoPreviousPage();
        });

        statsLayout.getChildren().addAll(canvas, nameBox, gridPane, backButton);

        return statsLayout;
    }

    private String durationToString(Duration duration) {
        long s = duration.getSeconds();
        return s >= 3600 ? String.format("%d:%02d:%02d", s / 3600, s % 3600 / 60, s % 60) : String.format("%d:%02d", s / 60, s % 60);
    }

    private VBox createHistoryLayout(UserData userData) {
        VBox historyLayout = new VBox();
        historyLayout.setSpacing(10);
        historyLayout.setPadding(new Insets(15));
        historyLayout.setStyle("-fx-background-radius: 10; -fx-background-color: " + CARD_COLOR + ";");
        historyLayout.setMaxWidth(500);
        historyLayout.setAlignment(Pos.CENTER);

        // Header row
        HBox headerRow = createRow("Date", "Level", "Score", "Duration", 0);
        historyLayout.getChildren().add(headerRow);

        // Create rows
        ArrayList<GameData> history = new ArrayList<>(userData.getHistory());
        history.sort(Comparator.comparing(GameData::getEndTime).reversed());
        for (GameData data : history) {
            String date = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(Date.from(data.getEndTime()));
            HBox row = createRow(date, String.valueOf(data.getLevel()), String.valueOf(data.getScore()), durationToString(data.getGameDuration()), data.getScore() == userData.getHighScore() ? 2 : 1);
            historyLayout.getChildren().add(row);
        }

        return historyLayout;
    }

    // type: 0 => header, 1 => normal text, 2 => colored text
    private HBox createRow(String date, String level, String score, String duration, int type) {
        String[] text = { date, level, score, duration };
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(10));

        // Apply background styles
        if (type != 0)
            row.setStyle("""
                    -fx-background-color: rgba(255, 255, 255, 0.1);
                    -fx-padding: 10px;
                    -fx-background-radius: 5px;
                    """);

        // Create labels for each column
        Label[] labels = new Label[4];
        switch (type) {
            case 0 -> {
                for (int i = 0; i < 4; i++) {
                    labels[i] = new BoldLabel(text[i]);
                    setLabelProperties(labels[i]);
                }

            }
            case 1 -> {
                for (int i = 0; i < 4; i++) {
                    labels[i] = new NeutralLabel(text[i]);
                    setLabelProperties(labels[i]);
                }
            }
            case 2 -> {
                for (int i = 0; i < 4; i++) {
                    labels[i] = new ColoredLabel(text[i], "#d3d3ff");
                    setLabelProperties(labels[i]);
                }
            }
        }

        // Add labels to the row
        row.getChildren().addAll(labels);

        return row;
    }

    private void setLabelProperties(Label label) {
        final double COLUMN_WIDTH = 150;
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMinWidth(COLUMN_WIDTH);
        label.setMaxWidth(Double.MAX_VALUE);
    }

}
