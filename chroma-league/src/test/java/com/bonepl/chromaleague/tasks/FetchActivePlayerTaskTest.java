package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FetchActivePlayerTaskTest {

    @BeforeAll
    static void beforeAll() {
        new GameStateMocks();
    }

    @Test
    void testActivePlayerParsing() {
        //given
        new LeagueHttpClientMock().mockActivePlayerResponse("json/activeplayer.json");

        //when
        new FetchActivePlayerTask().run();

        //then
        assertNotNull(RunningState.getGameState().getActivePlayer());

        final ActivePlayer activePlayer = RunningState.getGameState().getActivePlayer();
        assertEquals(123.45, activePlayer.currentGold());
        assertEquals(1, activePlayer.level());

        final ChampionStats championStats = activePlayer.championStats();
        assertEquals(255.59997, championStats.currentHealth());
        assertEquals(655.5999755859375, championStats.maxHealth());
        assertEquals(111.11111111111111, championStats.resourceValue());
        assertEquals(222.222222222222222, championStats.resourceMax());
        assertEquals("MANA", championStats.resourceType());
    }
}