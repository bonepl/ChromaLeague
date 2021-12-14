package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FetchGameStatsTaskTest {
    @BeforeAll
    static void beforeAll() {
        new GameStateMocks();
    }

    @Test
    void testGameStatsParsing() {
        //given
        new LeagueHttpClientMock().mockGameStatsResponse("json/gamestats.json");

        //when
        new FetchGameStatsTask().run();

        //then
        GameStats gameStats = RunningState.getGameState().getGameStats();
        assertNotNull(gameStats);
        assertEquals("CLASSIC", gameStats.gameMode());
        assertEquals(15.123456789, gameStats.gameTime());
        assertEquals("Infernal", gameStats.mapTerrain());
    }
}