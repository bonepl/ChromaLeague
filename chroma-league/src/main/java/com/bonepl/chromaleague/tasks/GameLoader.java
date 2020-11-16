package com.bonepl.chromaleague.tasks;

import com.bonepl.razersdk.ChromaRestSDK;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoader implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(GameLoader.class.getName());
    private static final int GAME_LOADING_REFRESH_DELAY = 50;

    private final ScheduledExecutorService gameLoaderExecutor;

    public GameLoader(final ChromaRestSDK chromaRestSDK) {
        gameLoaderExecutor = Executors.newSingleThreadScheduledExecutor();
        gameLoaderExecutor.scheduleWithFixedDelay(new GameLoadingTask(chromaRestSDK), 0, GAME_LOADING_REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        try {
            gameLoaderExecutor.shutdown();
            gameLoaderExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e, () -> "GameLoader interrupted while shutting down scheduler");
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Exception while shutting down GameLoader executor");
        }
    }
}
