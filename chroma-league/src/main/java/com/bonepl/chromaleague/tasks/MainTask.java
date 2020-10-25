package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.parts.MainHud;
import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.razersdk.RazerSDKClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTask implements Runnable {
    private static final Logger logger = LogManager.getLogger();

    private static ScheduledExecutorService scheduledExecutorService;
    private static RazerSDKClient razerSDKClient;

    @Override
    public void run() {
        if (GameState.isRiotApiUp()) {
            initializePreGame();
            if (GameState.isRunningGameChanged()) {
                if (GameState.isRunningGame()) {
                    initializeGameThreads();
                } else {
                    shutdown();
                }
            }
        }
    }

    private static void shutdown() {
        logger.info("Player left the game");
        razerSDKClient.close();
        razerSDKClient = null;
        scheduledExecutorService.shutdown();
    }

    private static void initializeGameThreads() {
        logger.info("Player joined the game");
        razerSDKClient = new RazerSDKClient();
        scheduledExecutorService.scheduleWithFixedDelay(new FetchPlayerListTask(), 0, 1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, 300, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(new EventAnimationProcessorTask(), 100, 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(() -> razerSDKClient.createKeyboardEffect(new MainHud()), 150, 50, TimeUnit.MILLISECONDS);
    }

    private static void initializePreGame() {
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            logger.info("Game is loading");
            scheduledExecutorService = Executors.newScheduledThreadPool(10);
            scheduledExecutorService.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, 1000, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new EventDataProcessorTask(), 500, 500, TimeUnit.MILLISECONDS);
        }
    }
}
