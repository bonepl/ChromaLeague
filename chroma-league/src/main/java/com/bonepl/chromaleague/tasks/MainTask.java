package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTask implements Runnable {
    public static final int ACTIVE_PLAYER_FETCH_DELAY = 100;
    public static final int EVENTS_FETCH_DELAY = 1000;
    public static final int MAIN_HUD_REFRESH_DELAY = 50;

    private static final Logger LOGGER = Logger.getLogger(MainTask.class.getName());

    private static ScheduledExecutorService mainExecutor;
    private static ChromaRestSDK chromaRestSDK;

    @Override
    public void run() {
        try {
            if (RunningState.isRiotApiUp()) {
                initializePreGame();
                if (RunningState.isRunningGameChanged() && RunningState.isRunningGame()) {
                    initializeGameThreads();
                }
            } else {
                shutdown();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Exception in MainTask");
        }
    }

    private static void shutdown() {
        if (chromaRestSDK != null || mainExecutor != null) {
            LOGGER.info("Player left the game");
            shutdownChromaSDK();
            shutdownMainExecutor();
            RunningState.setRunningGame(false);
        }
    }

    private static void shutdownMainExecutor() {
        if (mainExecutor != null) {
            try {
                mainExecutor.shutdown();
                mainExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, e, () -> "MainTask thread interrupted while shutting down scheduler");
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, ex, () -> "Exception while shutting down main executor");
            }
        }
        mainExecutor = null;
    }

    private static void shutdownChromaSDK() {
        if (chromaRestSDK != null) {
            try {
                chromaRestSDK.close();
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, ex, () -> "Exception while shutting down Chroma SDK");
            }
        }
        chromaRestSDK = null;
    }

    private static void initializeGameThreads() {
        LOGGER.info("Player joined the game");
        chromaRestSDK = new ChromaRestSDK();
        mainExecutor.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, ACTIVE_PLAYER_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new RefreshMainHudTask(chromaRestSDK), 150, MAIN_HUD_REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    private static void initializePreGame() {
        if (mainExecutor == null) {
            LOGGER.info("Game is loading");
            mainExecutor = Executors.newScheduledThreadPool(10);
            mainExecutor.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, EVENTS_FETCH_DELAY, TimeUnit.MILLISECONDS);
        }
    }
}
