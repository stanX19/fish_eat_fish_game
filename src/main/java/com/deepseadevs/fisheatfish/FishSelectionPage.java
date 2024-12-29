package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.FishTypes;
import com.deepseadevs.fisheatfish.game.Spawner;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
import com.deepseadevs.fisheatfish.widgets.buttons.AccentButton;
import com.deepseadevs.fisheatfish.widgets.buttons.SecondaryButton;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FishSelectionPage extends BasePage {
    private Canvas canvas;
    private AnimationTimer animationTimer;
    private GraphicsContext gc;

    private final Map<FishTypes, BaseFish> fishMap;
    private final Spawner spawner;
    private int currentFishIndex;
    private final int maxFishIndex;
    private final FishTypes[] fishTypeArray;
    private FishTypes selectedFishType;

    private Button selectButton;
    private Button previousButton;
    private Button nextButton;
    private Button startButton;
    private Button backButton;

    public FishSelectionPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
        this.spawner = new Spawner();
        this.fishMap = new EnumMap<>(FishTypes.class);
        this.fishTypeArray = List.of(FishTypes.PLAYER_SMALL, FishTypes.PLAYER_MEDIUM, FishTypes.PLAYER_LARGE, FishTypes.PLAYER_GIANT).toArray(new FishTypes[0]);
        this.currentFishIndex = 0;
        this.selectedFishType = sessionManager.getUserFishType();
        this.maxFishIndex = HistoryParser.of(sessionManager.getGameHistory()).getHighestLevel();

        initializeFishMap();
        initializeAnimationTimer();
        updateSelectButtonState();
        startButton.requestFocus();
    }

    private void initializeFishMap() {
        for (FishTypes fishType : fishTypeArray) {
            BaseFish fish = spawner.spawnFish(fishType);
            fish.setXv(1);
            fishMap.put(fishType, fish);
        }
    }


    @Override
    protected Scene createScene() {
        StackPane root = new StackPane();

        root.setStyle("-fx-background-color: skyblue;");
        // Canvas for fish rendering
        canvas = new Canvas(540, 360);
        gc = canvas.getGraphicsContext2D();

        // HBox for button alignment
        HBox topButtonBox = new HBox(20);
        topButtonBox.setAlignment(Pos.CENTER);

        previousButton = new MainButton("Previous");
        previousButton.setMinWidth(80);
        previousButton.setOnAction(e -> showPreviousFish());

        selectButton = new MainButton("Select");
        selectButton.setMinWidth(100);
        selectButton.setOnAction(e -> selectCurrentFish());

        nextButton = new MainButton("Next");
        nextButton.setMinWidth(80);
        nextButton.setOnAction(e -> showNextFish());

        topButtonBox.getChildren().addAll(previousButton, selectButton, nextButton);

        HBox botButtonBox = new HBox(20);
        botButtonBox.setAlignment(Pos.CENTER);
        botButtonBox.setMaxWidth(400);

        backButton = new AccentButton("Back");
        backButton.setMinWidth(50);
        backButton.setOnAction(e -> {
            stopAnimationTimer();
            uiController.gotoMainMenu();
        });

        startButton = new SecondaryButton("Start Game");
        startButton.setMinWidth(230);
        startButton.setOnAction(e -> {
            stopAnimationTimer();
            uiController.gotoGamePage();
        });

        botButtonBox.getChildren().addAll(backButton, startButton);
        VBox buttonsContainer = new VBox(20);
        buttonsContainer.setTranslateY(100); // Y offset
        buttonsContainer.setAlignment(Pos.CENTER); // Center align the contents of VBox
        StackPane.setAlignment(buttonsContainer, Pos.CENTER); // Center align VBox in StackPane

        buttonsContainer.getChildren().addAll(topButtonBox, botButtonBox);
        root.getChildren().addAll(canvas, buttonsContainer);
        Scene scene = new Scene(root);
        scene.setFill(Color.LIGHTBLUE);
        return scene;
    }


    private void initializeAnimationTimer() {
        animationTimer = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                renderCurrentFish(deltaTime);
            }
        };
        animationTimer.start();
    }

    private void stopAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void renderCurrentFish(double deltaTime) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        BaseFish currentFish = getCurrentFish();
        currentFish.update(deltaTime);
        currentFish.setX(canvas.getWidth() / 2 - currentFish.getWidth() / 2);
        currentFish.setY(canvas.getHeight() / 2 - currentFish.getHeight() / 2);
        currentFish.render(gc);
    }

    private void showPreviousFish() {
        currentFishIndex = (currentFishIndex - 1 + fishTypeArray.length) % fishTypeArray.length;
        updateSelectButtonState();
    }

    private void showNextFish() {
        currentFishIndex = (currentFishIndex + 1) % fishTypeArray.length;
        updateSelectButtonState();
    }

    private void selectCurrentFish() {
        selectedFishType = getCurrentFishType();
        sessionManager.setUserFishType(selectedFishType);
        updateSelectButtonState();
    }

    private void updateSelectButtonState() {
        FishTypes currentFishType = getCurrentFishType();
        if (currentFishType == selectedFishType) {
            selectButton.setDisable(true);
            selectButton.setText("Selected");
        } else if (currentFishIndex >= maxFishIndex) {
            selectButton.setDisable(true);
            selectButton.setText("Locked");
        } else{
            selectButton.setDisable(false);
            selectButton.setText("Select");
        }
    }

    private BaseFish getCurrentFish() {
        return fishMap.get(getCurrentFishType());
    }

    private FishTypes getCurrentFishType() {
        return fishTypeArray[currentFishIndex];
    }
}
