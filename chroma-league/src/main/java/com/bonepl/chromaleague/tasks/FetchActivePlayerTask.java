package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.state.GameState;
import com.jsoniter.JsonIterator;

public class FetchActivePlayerTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayer";

    @Override
    public void run() {
        LeagueHttpClient.get(URL)
                .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class))
                .ifPresent(GameState::setActivePlayer);
    }
}
