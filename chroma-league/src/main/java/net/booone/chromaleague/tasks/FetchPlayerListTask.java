package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClients;
import net.booone.chromaleague.rest.http.handlers.PlayerListResponseHandler;
import net.booone.chromaleague.state.RunningState;

public class FetchPlayerListTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/playerlist";
    private final PlayerListResponseHandler playerListResponseHandler = new PlayerListResponseHandler();

    @Override
    public void run() {
        LeagueHttpClients.getNonBlockingResponse(URL, playerListResponseHandler)
                .ifPresent(playerList -> RunningState.getGameState().setPlayerList(playerList));
    }
}
