package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.CheckRiotApiTask;
import com.bonepl.chromaleague.tasks.MainTask;
import com.bonepl.chromaleague.tasks.SdkConnectivityCheckTask;
import com.bonepl.chromaleague.tasks.VersionCheckTask;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChromaLeague implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(ChromaLeague.class.getName());
    private final ScheduledExecutorService mainExecutorService = Executors.newScheduledThreadPool(5);

    public void runChromaLeague() {
        mainExecutorService.execute(new VersionCheckTask());
        mainExecutorService.execute(new SdkConnectivityCheckTask());
        LOGGER.info("Started Chroma League, waiting for a game - press ENTER to exit");
        mainExecutorService.scheduleWithFixedDelay(new CheckRiotApiTask(), 0L, 1000L, TimeUnit.MILLISECONDS);
        mainExecutorService.scheduleWithFixedDelay(new MainTask(), 100L, 500L, TimeUnit.MILLISECONDS);
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            scanner.nextLine();
        }
    }

    @Override
    public void close() {
        mainExecutorService.shutdown();
        LeagueHttpClient.shutdown();
        RunningState.getRiotApi().reset();
        RunningState.getRunningGame().reset();
    }
}
