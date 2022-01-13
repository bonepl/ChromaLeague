package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

import static net.booone.chromaleague.rest.http.handlers.ActivePlayerResponseHandlerTest.verifyActivePlayer;

class FetchActivePlayerTaskTest {

    @Test
    void testActivePlayerParsing() {
        //given
        new LeagueHttpClientMock().mockActivePlayerResponse("json/activeplayer.json");

        //when
        new FetchActivePlayerTask().run();

        //then
        verifyActivePlayer(RunningState.getGameState().getActivePlayer());
    }
}