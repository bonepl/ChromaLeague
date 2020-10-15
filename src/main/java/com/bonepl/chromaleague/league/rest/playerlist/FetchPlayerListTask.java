package com.bonepl.chromaleague.league.rest.playerlist;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClient;
import com.bonepl.chromaleague.league.rest.playerlist.model.Player;
import com.bonepl.chromaleague.league.rest.playerlist.model.PlayerList;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchPlayerListTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/playerlist";
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        if (GameState.isGameActive()) {
            logger.trace("Fetching PlayerList");
            LeagueHttpClient.get(URL)
                    .map(playerList -> JsonIterator.deserialize(playerList, Player[].class))
                    .map(PlayerList::new)
                    .ifPresentOrElse(GameState::setPlayerList,
                            () -> GameState.setPlayerList(null));
        }
    }

}
