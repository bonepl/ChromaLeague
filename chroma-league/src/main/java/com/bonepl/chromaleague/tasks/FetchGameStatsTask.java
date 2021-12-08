package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.GameStatsResponseHandler;
import com.bonepl.chromaleague.rest.NewLeagueHttpClient;
import com.bonepl.chromaleague.state.RunningState;

import java.util.logging.Logger;

public class FetchGameStatsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";
    public static final String DEFAULT_MAP_TERRAIN = "Default";
    private static final Logger LOGGER = Logger.getLogger(FetchGameStatsTask.class.getName());
    private final GameStatsResponseHandler gameStatsResponseHandler = new GameStatsResponseHandler();

    @Override
    public void run() {
        NewLeagueHttpClient.getResponse(URL, gameStatsResponseHandler)
                .ifPresent(gameStats -> RunningState.getGameState().setGameStats(gameStats));
    }
}
