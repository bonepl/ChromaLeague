package com.bonepl.chromaleague.rest.activeplayername;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClientMocker;
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