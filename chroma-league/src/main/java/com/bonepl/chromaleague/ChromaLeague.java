package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.RefreshMainHudTask;
import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.tasks.FetchPlayerListTask;
import com.bonepl.chromaleague.tasks.EventAnimationProcessorTask;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import com.bonepl.razersdk.RazerSDKClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChromaLeague {
    private static final Logger logger = LogManager.getLogger();
    private static ScheduledExecutorService scheduledExecutorService;
    private static RazerSDKClient razerSDKClient;
    static boolean alive = true;

    public static void main(String... args) {
        logger.info("Started Chroma League");
        final ScheduledExecutorService riotApiCheckExecutor = Executors.newSingleThreadScheduledExecutor();
        riotApiCheckExecutor.scheduleAtFixedRate(new CheckRiotApiTask(), 0, 1000, TimeUnit.MILLISECONDS);

        while (alive) {
            if (GameState.isRiotApiUp()) {
                initializePreGameThreads();
                while (GameState.isRiotApiUp()) {
                    if (GameState.isRunningGame()) {
                        initializeGameThreads();
                    }
                }
                shutdownThreads();
            }
        }

        riotApiCheckExecutor.shutdown();
        LeagueHttpClient.shutdown();
    }

    private static void shutdownThreads() {
        if (razerSDKClient != null) {
            razerSDKClient.close();
            razerSDKClient = null;
        }

        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            logger.info("RIOT api shut downed, cleaning up");
            scheduledExecutorService.shutdown();
        }
    }

    private static void initializeGameThreads() {
        if (razerSDKClient == null) {
            razerSDKClient = new RazerSDKClient();
            scheduledExecutorService.scheduleWithFixedDelay(new FetchPlayerListTask(), 0, 1000, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new FetchActivePlayerTask(), 50, 300, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new EventAnimationProcessorTask(), 100, 500, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new RefreshMainHudTask(razerSDKClient), 150, 50, TimeUnit.MILLISECONDS);

        }
    }

    private static void initializePreGameThreads() {
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            scheduledExecutorService = Executors.newScheduledThreadPool(20);
            scheduledExecutorService.scheduleWithFixedDelay(new FetchNewEventsTask(), 0, 1000, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new EventDataProcessorTask(), 500, 500, TimeUnit.MILLISECONDS);
        }
    }
}
