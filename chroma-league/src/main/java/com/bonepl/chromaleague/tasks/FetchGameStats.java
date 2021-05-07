package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.jsoniter.JsonIterator;

public class FetchGameStats {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";

    public GameStats fetchGameStats() {
        return LeagueHttpClient.getSingleResponse(URL)
                .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class)).orElseThrow();
    }
}
