package com.bonepl.chromaleague.league.rest.activeplayer;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClient;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchActivePlayerTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayer";
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.trace("Fetching Active Player");
        if (GameState.isGameActive()) {
            LeagueHttpClient.get(URL)
                    .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class))
                    .ifPresentOrElse(GameState::setActivePlayer,
                            () -> GameState.setActivePlayer(null));
        }
    }

}
