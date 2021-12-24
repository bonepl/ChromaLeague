package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.http.LeagueHttpClients;
import com.bonepl.chromaleague.rest.http.handlers.PlayerNameResponseHandler;
import com.bonepl.chromaleague.state.RunningState;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchPlayerNameTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayername";
    private static final Logger LOGGER = Logger.getLogger(FetchPlayerNameTask.class.getName());
    private static final PlayerNameResponseHandler playerNameResponseHandler = new PlayerNameResponseHandler();

    @Override
    public void run() {
        try {
            RunningState.getGameState()
                    .setPlayerName(LeagueHttpClients.getRetryingResponse(URL, playerNameResponseHandler));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching player name");
            throw new IllegalStateException(ex);
        }
    }
}