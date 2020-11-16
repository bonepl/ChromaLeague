package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainThreads implements Closeable {
    public static final int ACTIVE_PLAYER_FETCH_DELAY = 100;
    public static final int EVENTS_FETCH_DELAY = 300;
    public static final int MAIN_HUD_REFRESH_DELAY = 50;

    private static final Logger LOGGER = Logger.getLogger(MainThreads.class.getName());

    private final ScheduledExecutorService mainExecutor;
    private final GameLoader gameLoader;
    private final ChromaRestSDK chromaRestSDK;
    private boolean alive;

    public MainThreads() {
        alive = true;
        LOGGER.info("Game is loading");
        mainExecutor = Executors.newScheduledThreadPool(10);
        mainExecutor.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, EVENTS_FETCH_DELAY, TimeUnit.MILLISECONDS);
        chromaRestSDK = new ChromaRestSDK();
        gameLoader = new GameLoader(chromaRestSDK);
    }

    public void initializeGameThreads() {
        LOGGER.info("Player joined the game");
        gameLoader.close();
        RunningState.getGameState().setPlayerName(new FetchPlayerName().fetchPlayerName());
        RunningState.getGameState().setPlayerList(new FetchPlayerList().fetchPlayerList());
        mainExecutor.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, ACTIVE_PLAYER_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new RefreshMainHudTask(chromaRestSDK), 150, MAIN_HUD_REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        alive = false;
        RunningState.setRunningGame(false);
        shutdownMainExecutor();
        shutdownChromaSDK();
        LOGGER.info("Player left the game, waiting for another...");
    }

    private void shutdownMainExecutor() {
        try {
            mainExecutor.shutdown();
            mainExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e, () -> "MainThreads interrupted while shutting down scheduler");
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Exception while shutting down main executor");
        }
    }

    private void shutdownChromaSDK() {
        try {
            chromaRestSDK.close();
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Exception while shutting down Chroma SDK");
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
