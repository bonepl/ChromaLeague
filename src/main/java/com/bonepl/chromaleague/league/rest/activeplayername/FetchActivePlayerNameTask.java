package com.bonepl.chromaleague.league.rest.activeplayername;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchActivePlayerNameTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayername";
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.trace("Fetching Active Player Name");
        LeagueHttpClient.get(URL)
                .filter(name -> !name.contains("RESOURCE_NOT_FOUND"))
                .map(name -> name.substring(1, name.length() - 1))
                .ifPresentOrElse(GameState::setActivePlayerName,
                        () -> GameState.setActivePlayerName(null));
    }
}
