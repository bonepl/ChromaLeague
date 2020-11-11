package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.jsoniter.JsonIterator;

import java.util.logging.Logger;

public class FetchGameStats {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";
    private static final Logger LOGGER = Logger.getLogger(FetchGameStats.class.getName());

    public GameStats fetchGameStats() {
        return LeagueHttpClient.getResponse(URL)
                .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class)).orElseThrow();
    }
}
