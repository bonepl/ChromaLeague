package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.tasks.MainTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ChromaLeague {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ScheduledExecutorService MAIN_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(5);
    public static final int MAIN_EXECUTOR_DELAY = 500;

    private ChromaLeague() {
    }

    @SuppressWarnings("BusyWait")
    public static void main(String... args) {
        LOGGER.info("Started Chroma League");
        MAIN_EXECUTOR_SERVICE.scheduleWithFixedDelay(new CheckRiotApiTask(), 0, MAIN_EXECUTOR_DELAY, TimeUnit.MILLISECONDS);
        MAIN_EXECUTOR_SERVICE.scheduleWithFixedDelay(new MainTask(), 100, MAIN_EXECUTOR_DELAY, TimeUnit.MILLISECONDS);
        while (!MAIN_EXECUTOR_SERVICE.isShutdown()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
        LeagueHttpClient.shutdown();
    }
}
