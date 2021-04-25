package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FetchGameStatsTest {
    @BeforeAll
    static void beforeAll() {
        new GameStateMocks();
    }

    @Test
    void testGameStatsParsing() {
        //given
        new LeagueHttpClientMock().mockGameStatsResponse("json/gamestats.json");

        //when
        final GameStats gameStats = new FetchGameStats().fetchGameStats();

        //then
        assertNotNull(gameStats);
        assertEquals("CLASSIC", gameStats.gameMode());
        assertEquals(15.123456789, gameStats.gameTime());
    }
}