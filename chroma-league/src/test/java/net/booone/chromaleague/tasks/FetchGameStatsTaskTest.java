package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

import static net.booone.chromaleague.rest.http.handlers.GameStatsResponseHandlerTest.verifyGameStats;

class FetchGameStatsTaskTest {

    @Test
    void testGameStatsParsing() {
        //given
        new LeagueHttpClientMock().mockGameStatsResponse("json/gamestats.json");

        //when
        new FetchGameStatsTask().run();

        //then
        verifyGameStats(RunningState.getGameState().getGameStats());
    }
}