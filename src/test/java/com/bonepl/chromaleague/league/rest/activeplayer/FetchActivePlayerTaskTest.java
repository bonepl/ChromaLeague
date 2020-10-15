package com.bonepl.chromaleague.league.rest.activeplayer;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ChampionStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FetchActivePlayerTaskTest {

    @Test
    void testActivePlayerParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/activeplayer.json");
        GameState.setActivePlayerName("BooonE");

        //when
        new FetchActivePlayerTask().run();

        //then
        assertTrue(GameState.isActivePlayerAvailable());

        final ActivePlayer activePlayer = GameState.getActivePlayer();
        assertEquals(123.45, activePlayer.getCurrentGold());

        final ChampionStats championStats = activePlayer.getChampionStats();
        assertEquals(255.59997, championStats.getCurrentHealth());
        assertEquals(655.5999755859375, championStats.getMaxHealth());
        assertEquals(111.11111111111111, championStats.getResourceValue());
        assertEquals(222.222222222222222, championStats.getResourceMax());
        assertEquals("MANA", championStats.getResourceType());
    }
}