package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchGameStatsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";
    public static final String DEFAULT_MAP_TERRAIN = "Default";
    private static final Logger LOGGER = Logger.getLogger(FetchGameStatsTask.class.getName());

    @Override
    public void run() {
        try {
            LeagueHttpClient.getSingleResponse(URL)
                    .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class))
                    .ifPresent(gameStats -> RunningState.getGameState().setGameStats(gameStats));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching GameStats");
        }
    }
}
