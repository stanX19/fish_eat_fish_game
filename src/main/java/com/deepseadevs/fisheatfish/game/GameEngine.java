package com.deepseadevs.fisheatfish.game;

import com.deepseadevs.fisheatfish.SessionManager;
import com.deepseadevs.fisheatfish.game.fish.BaseFish;
import com.deepseadevs.fisheatfish.game.fish.MediumFish;
import com.deepseadevs.fisheatfish.game.fish.PlayerFish;
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
    private AnimationTimer gameLoop;
    private Runnable gameOverCallback;
    private static final int ENEMY_PER_LEVEL = 30;
    private static final int KILLS_PER_LEVEL = 30;
    private static final int MID_LEVEL_THRESHOLD = KILLS_PER_LEVEL / 2;
    private final GameData gameData;

    public GameEngine(GraphicsContext gc, SessionManager sessionManager) {
        this.gc = gc;
        this.sessionManager = sessionManager;
        this.gameData = new GameData(); // TODO: use sessionManager's game data instead
        this.bound = new Bound(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        this.spawner = new Spawner(this.bound);
        this.fishHandler = new FishHandler(this.bound);
        this.gameRenderer = new GameRenderer(gc, this.fishHandler, this.gameData);

        // Initialize the player and add to fishHandler
        this.player = new PlayerFish();
        this.player.setX(this.bound.maxX / 2);
        this.player.setY(this.bound.maxY / 2);
        this.fishHandler.addFish(player);

        this.playerHandler = new PlayerHandler(player);
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
                gameRenderer.render();
            }
        };
    }

    private void spawnFishes() {
        // TODO:
        //  follow level when spawning, currently its blindly spawning small and medium
        for (int i = 0; i < ENEMY_PER_LEVEL - fishHandler.getFishCount(); i++) {
            BaseFish newFish = spawner.spawnRandomFish(FishTypes.SMALL, FishTypes.MEDIUM);
            fishHandler.addFish(newFish);
        }
    }


    private void update(double deltaTime) {
        playerHandler.updatePlayerVelocity(deltaTime);
        fishHandler.updateAll(deltaTime);
        fishHandler.collideAll();
        fishHandler.renderAll(gc);
        gameData.updateDuration(deltaTime);
        spawnFishes();
        checkLevelProgression();
        syncGameData();

        if (!fishHandler.containsFish(player)) {
            triggerGameOver();
        }

    }

    private void checkLevelProgression() {
        // TODO:
        //  apply level change via levelHandler
        //  increase gameData.level when reach certain criteria
        //  idea: look at kills

    }

    private void syncGameData() {
        // TODO:
        //  use a better way to determine score......if there is one
        gameData.setFishEaten(player.getFishEaten());
        gameData.setScore((int)player.getArea() * 100L);
    }

    private void triggerGameOver() {
        gameData.setEnded(true);
        if (gameOverCallback != null) {
            gameOverCallback.run();
        }
    }
}
