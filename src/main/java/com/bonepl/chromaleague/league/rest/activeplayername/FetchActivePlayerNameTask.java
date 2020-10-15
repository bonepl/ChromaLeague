package com.bonepl.chromaleague.league.rest.activeplayername;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.ApacheLeagueHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchActivePlayerNameTask implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Fetching Active Player Name");
        ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/activeplayername")
                .filter(name -> !name.contains("RESOURCE_NOT_FOUND"))
                .map(name -> name.substring(1, name.length() - 1))
                .ifPresentOrElse(GameState::setActivePlayerName,
                        () -> GameState.setActivePlayerName(null));
    }
}
