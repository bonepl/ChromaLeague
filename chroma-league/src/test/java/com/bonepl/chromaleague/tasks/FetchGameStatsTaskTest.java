package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.rest.http.handlers.GameStatsResponseHandlerTest.verifyGameStats;

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