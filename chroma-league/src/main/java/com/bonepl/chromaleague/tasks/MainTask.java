package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTask implements Runnable {
    public static final int PLAYER_LIST_FETCH_DELAY = 1000;
    public static final int ACTIVE_PLAYER_FETCH_DELAY = 200;
    public static final int MAIN_HUD_REFRESH_DELAY = 50;

    private static final Logger LOGGER = LogManager.getLogger();
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
            LOGGER.error("Exception in MainTask", ex);
        }
    }

    private static void shutdown() {
        if (chromaRestSDK != null) {
            LOGGER.info("Player left the game");
            chromaRestSDK.close();
            chromaRestSDK = null;

            mainExecutor.shutdown();
            try {
                mainExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }

            RunningState.setRunningGame(false);
        }
    }

    private static void initializeGameThreads() {
        LOGGER.info("Player joined the game");
        chromaRestSDK = new ChromaRestSDK();
        mainExecutor.scheduleWithFixedDelay(new FetchPlayerListTask(), 0, PLAYER_LIST_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, ACTIVE_PLAYER_FETCH_DELAY, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new RefreshMainHudTask(chromaRestSDK), 150, MAIN_HUD_REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    private static void initializePreGame() {
        if (mainExecutor == null || mainExecutor.isShutdown()) {
            LOGGER.info("Game is loading");
            mainExecutor = Executors.newScheduledThreadPool(10);
            mainExecutor.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, 1000, TimeUnit.MILLISECONDS);
        }
    }
}
