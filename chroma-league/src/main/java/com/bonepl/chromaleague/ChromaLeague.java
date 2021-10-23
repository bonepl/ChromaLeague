package com.bonepl.chromaleague;

import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.tasks.MainTask;
import com.bonepl.chromaleague.tasks.SdkConnectivityCheckTask;
import com.bonepl.chromaleague.tasks.VersionCheckTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChromaLeague {
    private static final Logger LOGGER = Logger.getLogger(ChromaLeague.class.getName());
    private final ScheduledExecutorService mainExecutorService = Executors.newScheduledThreadPool(5);

    public void runChromaLeague() {
        mainExecutorService.execute(new VersionCheckTask());
        mainExecutorService.execute(new SdkConnectivityCheckTask());
        LOGGER.info("Started Chroma League, close this window to exit the application");
        mainExecutorService.scheduleWithFixedDelay(new CheckRiotApiTask(), 0L, 1000L, TimeUnit.MILLISECONDS);
        mainExecutorService.scheduleWithFixedDelay(new MainTask(), 100L, 500L, TimeUnit.MILLISECONDS);
        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning(() -> "ChromaLeague thread interrupted while running");
        }
    }
}
