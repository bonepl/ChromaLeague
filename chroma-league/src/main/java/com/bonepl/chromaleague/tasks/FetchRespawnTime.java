package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchRespawnTime {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/playerlist";
    private static final Logger LOGGER = Logger.getLogger(FetchRespawnTime.class.getName());

    public double fetchPlayerRespawnTime() {
        try {
            return LeagueHttpClient.getResponse(URL)
                    .map(FetchRespawnTime::getRespawnTimeFromJson)
                    .orElseThrow(() -> new IllegalStateException("Couldn't fetch respawn time"));
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Error while fetching respawn time from PlayerList");
        }
        return 0.0;
    }

    private static double getRespawnTimeFromJson(byte... json) {
        final String summonerName = RunningState.getGameState().getPlayerName();
        return JsonIterator.deserialize(json).asList().stream()
                .filter(champion -> summonerName.equals(champion.toString("summonerName")))
                .map(player -> player.toDouble("respawnTimer")).findAny()
                .orElseThrow(() -> new IllegalStateException("Summoner not found for respawnTime: " + summonerName));
    }
}
