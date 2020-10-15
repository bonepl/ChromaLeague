package com.bonepl.chromaleague.league.rest.activeplayername;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClientMocker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchActivePlayerNameTaskTest {
    @Test
    void testActivePlayerNameFetching() {
        //given
        LeagueHttpClientMocker.mockReturnedResponse("\"BonE\"");
        GameState.setActivePlayerName(null);
        assertFalse(GameState.isGameActive());

        //when
        new FetchActivePlayerNameTask().run();

        //then
        assertTrue(GameState.isGameActive());
        assertEquals("BonE", GameState.getActivePlayerName());
    }
}