package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchActivePlayerTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayer";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void run() {
        try {
            LeagueHttpClient.getResponse(URL)
                    .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class))
                    .ifPresent(activePlayer -> RunningState.getGameState().setActivePlayer(activePlayer));
        } catch (Exception ex) {
            LOGGER.error("Error while fetching ActivePlayer", ex);
        }
    }
}
