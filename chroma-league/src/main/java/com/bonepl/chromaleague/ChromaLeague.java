package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.tasks.MainTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChromaLeague implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ScheduledExecutorService mainExecutorService = Executors.newScheduledThreadPool(5);

    public void runChromaLeague() {
        LOGGER.info("Started Chroma League");
        mainExecutorService.scheduleWithFixedDelay(new CheckRiotApiTask(), 0, 1000, TimeUnit.MILLISECONDS);
        mainExecutorService.scheduleWithFixedDelay(new MainTask(), 100, 500, TimeUnit.MILLISECONDS);
        while (isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
    }

    private static boolean isAlive() {
        return true;
    }

    @Override
    public void close() {
        mainExecutorService.shutdown();
        LeagueHttpClient.shutdown();
    }
}
