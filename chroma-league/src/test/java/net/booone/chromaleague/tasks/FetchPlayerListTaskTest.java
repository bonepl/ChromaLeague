package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static net.booone.chromaleague.GameStateMocks.PLAYER_NAME;
import static net.booone.chromaleague.rest.http.handlers.PlayerListResponseHandlerTest.verifyPlayerList;

class FetchPlayerListTaskTest {

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testPlayerListParsing() {
        //given
        RunningState.getGameState().setPlayerName(PLAYER_NAME);
        new LeagueHttpClientMock().mockPlayerListResponse("json/playerlist.json");

        //when
        new FetchPlayerListTask().run();

        //then
        verifyPlayerList(RunningState.getGameState().getPlayerList());
    }
}