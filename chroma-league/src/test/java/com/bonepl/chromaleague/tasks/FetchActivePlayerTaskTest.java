package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.rest.http.handlers.ActivePlayerResponseHandlerTest.verifyActivePlayer;

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