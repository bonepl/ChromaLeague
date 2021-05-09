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
    public static final long PLAYER_LIST_FETCH_DELAY = 500L;
    public static final long ACTIVE_PLAYER_FETCH_DELAY = 100L;
    public static final long EVENTS_FETCH_DELAY = 300L;
    public static final long MAIN_HUD_REFRESH_DELAY = 50L;

    private static final Logger LOGGER = Logger.getLogger(MainThreads.class.getName());

    private final ScheduledExecutorService mainExecutor;
    private final GameLoader gameLoader;
    private final ChromaRestSDK chromaRestSDK;
    private boolean alive;

    public MainThreads() {
        alive = true;
        LOGGER.info("Game is loading");
        chromaRestSDK = new ChromaRestSDK();
        gameLoader = new GameLoader(chromaRestSDK);
        mainExecutor = Executors.newScheduledThreadPool(10);
        mainExecutor.scheduleWithFixedDelay(new FetchNewEventsTask(), 0L, EVENTS_FETCH_DELAY, TimeUnit.MILLISECONDS);
    }

    public void initializeGameThreads() {
        LOGGER.info("Player joined the game");
        gameLoader.close();
        RunningState.getGameState().setPlayerName(FetchPlayerName.fetchPlayerName());
        mainExecutor.scheduleWithFixedDelay(new FetchPlayerListTask(), 50L, PLAYER_LIST_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50L, ACTIVE_PLAYER_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new RefreshMainHudTask(chromaRestSDK), 150L, MAIN_HUD_REFRESH_DELAY, TimeUnit.MILLISECONDS);
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
            if (!mainExecutor.awaitTermination(5000L, TimeUnit.MILLISECONDS)) {
                LOGGER.log(Level.WARNING, () -> "Couldn't terminate GameLoader executor - timed out");
            }
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
