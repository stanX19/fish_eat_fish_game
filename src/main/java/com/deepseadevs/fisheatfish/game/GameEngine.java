package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.SessionManager;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import com.deepseadevs.fisheatfish.game.level.LevelHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GameEngine {
    private final Bound bound;
    private final SessionManager sessionManager;
    private final GraphicsContext gc;
    private final FishHandler fishHandler;
    private final GameRenderer gameRenderer;
    private final BaseFish player;
    private final PlayerHandler playerHandler;
    private final Spawner spawner;
    private final LevelHandler levelHandler;

    private AnimationTimer gameLoop;
    private Runnable gameOverCallback;
    private final GameData gameData;

    public GameEngine(GraphicsContext gc, SessionManager sessionManager, GameData gameData) {
        this.gc = gc;
        this.sessionManager = sessionManager;
        this.gameData = gameData;
        this.bound = new Bound(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        this.spawner = new Spawner(this.bound);
        this.fishHandler = new FishHandler(this.bound);
        this.gameRenderer = new GameRenderer(gc, this.fishHandler, this.gameData, sessionManager);

        // Initialize the player and add to fishHandler
        this.player = this.spawner.spawnFish(sessionManager.getUserFishType());
        this.player.setX(this.bound.getMidX());
        this.player.setY(this.bound.getMidY());
        this.fishHandler.addFish(player);
        this.playerHandler = new PlayerHandler(player);
        this.levelHandler = new LevelHandler(this.gameData, this.playerHandler);

        this.gameData.setEnded(false);

        spawnFishes();
        initializeGameLoop();
    }

    public void setGameOverCallback(Runnable gameOverCallback) {
        this.gameOverCallback = gameOverCallback;
    }

    public void start() {
        gameLoop.start();
    }

    public void handleKeyPressed(KeyEvent event) {
        playerHandler.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        playerHandler.handleKeyReleased(event);
    }

    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                if (gameData.isEnded())
                    return;
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                update(deltaTime);
            }
        };
    }

    private void spawnFishes() {
        for (int i = 0; i < levelHandler.getMaxFishCount() - fishHandler.getFishCount(); i++) {
            int fishOutOfBoundCount = fishHandler.getFishCount() - fishHandler.getFishCountInBound();
            double bufferRatio = fishOutOfBoundCount / (double)levelHandler.getMaxFishCount();
            spawner.setBufferRatio(bufferRatio);
            BaseFish newFish = spawner.spawnRandomFish(levelHandler.getFishTypes());
            fishHandler.addFish(newFish);
        }
    }


    private void update(double deltaTime) {
        playerHandler.updatePlayerVelocity(deltaTime);
        levelHandler.updateProgress();
        fishHandler.updateAll(deltaTime);
        fishHandler.collideAll();
        gameRenderer.render();
        spawnFishes();
        checkLevelProgression();
        syncGameData(deltaTime);

        if (!fishHandler.containsFish(player)) {
            triggerGameOver();
        }

    }

    private void checkLevelProgression() {
        if (gameData.getProgress() >= 1.0) {
            if (gameData.getLevel() >= levelHandler.getTotalLevels())
                triggerGameOver();
            else
                levelHandler.incrementLevel();
        }
    }

    private void syncGameData(double deltaTime) {
        gameData.setFishEaten(playerHandler.getAccumulatedFishEaten());
        gameData.setLevelFishEaten(player.getFishEaten());
        gameData.setScore((int)player.getArea() + player.getFishEaten() * 100L);
        gameData.updateDuration(deltaTime);
        sessionManager.updateHighScore(gameData.getScore());
        sessionManager.commit();
    }

    private void triggerGameOver() {
        gameData.setEnded(true);
        sessionManager.commit();
        if (gameOverCallback != null) {
            gameOverCallback.run();
        }
    }
}
