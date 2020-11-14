package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchPlayerList {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/playerlist";
    private static final Logger LOGGER = Logger.getLogger(FetchPlayerList.class.getName());

    public PlayerList fetchPlayerList() {
        try {
            if (RunningState.getGameState().isActivePlayerAvailable()) {
                return LeagueHttpClient.getResponse(URL)
                        .map(playerList -> JsonIterator.deserialize(playerList, Player[].class))
                        .map(PlayerList::new)
                        .orElseThrow(() -> new IllegalStateException("Couldn't fetch PlayerList"));
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching PlayerList");
        }
        return null;
    }
}
