package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.tasks.MainTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChromaLeague {
    private static final Logger logger = LogManager.getLogger();
    private static final ScheduledExecutorService mainExecutorService = Executors.newScheduledThreadPool(5);

    public static void main(String... args) {
        logger.info("Started Chroma League");
        mainExecutorService.scheduleWithFixedDelay(new CheckRiotApiTask(), 0, 500, TimeUnit.MILLISECONDS);
        mainExecutorService.scheduleWithFixedDelay(new MainTask(), 100, 500, TimeUnit.MILLISECONDS);
        while (!mainExecutorService.isShutdown()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        mainExecutorService.shutdown();
        LeagueHttpClient.shutdown();
    }
}
