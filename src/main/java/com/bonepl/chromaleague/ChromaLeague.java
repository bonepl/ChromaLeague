package com.bonepl.chromaleague;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.hud.RefreshMainHudTask;
import com.bonepl.chromaleague.league.rest.LeagueHttpClient;
import com.bonepl.chromaleague.league.rest.activeplayer.FetchActivePlayerTask;
import com.bonepl.chromaleague.league.rest.activeplayername.FetchActivePlayerNameTask;
import com.bonepl.chromaleague.league.rest.eventdata.FetchNewEventsTask;
import com.bonepl.chromaleague.league.rest.playerlist.FetchPlayerListTask;
import com.bonepl.chromaleague.razer.RazerSDKClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChromaLeague {
    private static ScheduledExecutorService scheduledExecutorService;
    private static RazerSDKClient razerSDKClient;
    static boolean alive = true;

    public static void main(String... args) {
        final ScheduledExecutorService gameActiveExecutor = Executors.newSingleThreadScheduledExecutor();
        gameActiveExecutor.scheduleAtFixedRate(new FetchActivePlayerNameTask(), 0, 1000, TimeUnit.MILLISECONDS);

        while (alive) {
            if (GameState.isGameActive()) {
                initializeThreads();
            } else {
                shutdownThreads();
            }
        }

        gameActiveExecutor.shutdown();
        LeagueHttpClient.shutdown();
    }

    private static void initializeThreads() {
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            razerSDKClient = new RazerSDKClient();
            scheduledExecutorService = Executors.newScheduledThreadPool(5);
            scheduledExecutorService.scheduleAtFixedRate(new FetchPlayerListTask(), 0, 1000, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new FetchActivePlayerTask(), 50, 300, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new FetchNewEventsTask(), 100, 1000, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new RefreshMainHudTask(razerSDKClient), 150, 100, TimeUnit.MILLISECONDS);
        }
    }

    private static void shutdownThreads() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            razerSDKClient.close();
            scheduledExecutorService.shutdown();
        }
    }


}
