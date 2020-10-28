package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.parts.EventAnimator;
import com.bonepl.chromaleague.hud.MainHud;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.ChromaRestSDK;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    private static ScheduledExecutorService mainExecutor;
    private static ChromaRestSDK chromaRestSDK;

    @Override
    public void run() {
        if (GameState.isRiotApiUp()) {
            initializePreGame();
            if (GameState.isRunningGameChanged() && GameState.isRunningGame()) {
                initializeGameThreads();
            }
        } else {
            shutdown();
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
            GameState.setRunningGame(false);
            GameStateHelper.resetCustomData();
            EventAnimator.stop();
            FetchNewEventsTask.resetProcessedEventCounter();
        }
    }

    private static void initializeGameThreads() {
        LOGGER.info("Player joined the game");
        chromaRestSDK = new ChromaRestSDK();
        mainExecutor.scheduleWithFixedDelay(new FetchPlayerListTask(), 0, 1000, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, 300, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(new EventAnimationProcessorTask(), 100, 500, TimeUnit.MILLISECONDS);
        mainExecutor.scheduleWithFixedDelay(() -> chromaRestSDK.createKeyboardEffect(new MainHud()), 150, 50, TimeUnit.MILLISECONDS);
    }

    private static void initializePreGame() {
        if (mainExecutor == null || mainExecutor.isShutdown()) {
            LOGGER.info("Game is loading");
            mainExecutor = Executors.newScheduledThreadPool(10);
            mainExecutor.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, 1000, TimeUnit.MILLISECONDS);
            mainExecutor.scheduleWithFixedDelay(new EventDataProcessorTask(), 500, 500, TimeUnit.MILLISECONDS);
        }
    }
}
