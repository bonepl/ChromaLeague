package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FetchActivePlayerTaskTest {

    @BeforeAll
    static void beforeAll() {
        GameStateMocks.mockActivePlayer("BooonE");
    }

    @AfterAll
    static void afterAll() {
        GameStateMocks.clearActivePlayer();
    }

    @Test
    void testActivePlayerParsing() {
        //given
        new LeagueHttpClientMock().mockActivePlayerResponse("json/activeplayer.json");

        //when
        new FetchActivePlayerTask().run();

        //then
        assertTrue(RunningState.getGameState().isActivePlayerAvailable());

        final ActivePlayer activePlayer = RunningState.getGameState().getActivePlayer();
        assertEquals(123.45, activePlayer.getCurrentGold());
        assertEquals(1, activePlayer.getLevel());
        assertEquals("BooonE", activePlayer.getSummonerName());

        final ChampionStats championStats = activePlayer.getChampionStats();
        assertEquals(255.59997, championStats.getCurrentHealth());
        assertEquals(655.5999755859375, championStats.getMaxHealth());
        assertEquals(111.11111111111111, championStats.getResourceValue());
        assertEquals(222.222222222222222, championStats.getResourceMax());
        assertEquals("MANA", championStats.getResourceType());
    }
}