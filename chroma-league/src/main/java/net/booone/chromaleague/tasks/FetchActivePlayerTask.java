package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClients;
import net.booone.chromaleague.rest.http.handlers.ActivePlayerResponseHandler;
import net.booone.chromaleague.state.RunningState;

public class FetchActivePlayerTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/activeplayer";
    private final ActivePlayerResponseHandler activePlayerResponseHandler = new ActivePlayerResponseHandler();

    @Override
    public void run() {
        LeagueHttpClients.getNonBlockingResponse(URL, activePlayerResponseHandler)
                .ifPresent(activePlayer -> RunningState.getGameState().setActivePlayer(activePlayer));
    }
}
