package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.GameState;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchPlayerListTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/playerlist";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void run() {
        if (GameState.isActivePlayerAvailable()) {
            LeagueHttpClient.getResponse(URL)
                    .map(playerList -> JsonIterator.deserialize(playerList, Player[].class))
                    .map(PlayerList::new)
                    .ifPresent(GameState::setPlayerList);
        }
    }

}
