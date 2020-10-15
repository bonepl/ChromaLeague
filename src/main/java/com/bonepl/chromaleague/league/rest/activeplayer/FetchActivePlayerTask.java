package com.bonepl.chromaleague.league.rest.activeplayer;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.ApacheLeagueHttpClient;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchActivePlayerTask implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Fetching Active Player");
        if (GameState.isGameActive()) {
            ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/activeplayer")
                    .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class))
                    .ifPresentOrElse(GameState::setActivePlayer,
                            () -> GameState.setActivePlayer(null));
        }
    }

}
