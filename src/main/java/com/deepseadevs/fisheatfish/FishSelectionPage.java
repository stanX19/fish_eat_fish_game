package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.Spawner;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import com.deepseadevs.fisheatfish.game.FishTypes;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FishSelectionPage extends BasePage {
    private Canvas canvas;
    private AnimationTimer animationTimer;
    private GraphicsContext gc;

    private Map<FishTypes, BaseFish> fishMap;
    private Spawner spawner;
    private int currentFishIndex;
    private FishTypes[] fishTypeArray;
    private FishTypes selectedFishType;

    private Button selectButton;
    private Button previousButton;
    private Button nextButton;

    public FishSelectionPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
        this.spawner = new Spawner();
        this.fishMap = new EnumMap<>(FishTypes.class);
        this.fishTypeArray = List.of(FishTypes.PLAYER_SMALL, FishTypes.PLAYER_MEDIUM, FishTypes.PLAYER_LARGE, FishTypes.PLAYER_GIANT).toArray(new FishTypes[0]);
        this.currentFishIndex = 0;
        this.selectedFishType = sessionManager.getUserFishType(); // No fish selected initially

        initializeFishMap();
        initializeAnimationTimer();
        updateSelectButtonState();
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

        // Canvas for fish rendering
        canvas = new Canvas(1280, 720);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // HBox for button alignment
        HBox buttonBox = new HBox(20);
        buttonBox.setTranslateY(100); // Y offset
        buttonBox.setAlignment(Pos.CENTER); // Center align the contents of HBox
        StackPane.setAlignment(buttonBox, Pos.CENTER); // Center align HBox in StackPane

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            stopAnimationTimer();
            uiController.gotoMainMenu();
        });

        previousButton = new Button("Previous");
        previousButton.setOnAction(e -> showPreviousFish());

        selectButton = new Button("Select");
        selectButton.setOnAction(e -> selectCurrentFish());

        nextButton = new Button("Next");
        nextButton.setOnAction(e -> showNextFish());

        buttonBox.getChildren().addAll(previousButton, selectButton, nextButton, backButton);
        root.getChildren().add(buttonBox);

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
        } else {
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
