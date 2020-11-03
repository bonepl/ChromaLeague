package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchGameStats {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";
    private static final Logger LOGGER = LogManager.getLogger();

    public GameStats fetchGameStats() {
        return LeagueHttpClient.getResponse(URL)
                .map(activePlayer -> JsonIterator.deserialize(activePlayer, GameStats.class)).orElseThrow();
    }
}
