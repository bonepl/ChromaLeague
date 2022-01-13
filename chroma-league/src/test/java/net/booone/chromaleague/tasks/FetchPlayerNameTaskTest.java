package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FetchPlayerNameTaskTest {

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testPlayerNameFetch() {
        //given
        new LeagueHttpClientMock().mockPlayerNameResponse("json/playername.json");

        //when
        new FetchPlayerNameTask().run();

        //then
        assertEquals("BooonE", RunningState.getGameState().getPlayerName());
    }
}