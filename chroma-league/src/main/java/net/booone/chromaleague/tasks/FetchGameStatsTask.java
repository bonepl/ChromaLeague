package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClients;
import net.booone.chromaleague.rest.http.handlers.GameStatsResponseHandler;
import net.booone.chromaleague.state.RunningState;

public class FetchGameStatsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/gamestats";
    public static final String DEFAULT_MAP_TERRAIN = "Default";
    private final GameStatsResponseHandler gameStatsResponseHandler = new GameStatsResponseHandler();

    @Override
    public void run() {
        LeagueHttpClients.getNonBlockingResponse(URL, gameStatsResponseHandler)
                .ifPresent(gameStats -> RunningState.getGameState().setGameStats(gameStats));
    }
}
