package com.bonepl.chromaleague.rest.activeplayer;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FetchActivePlayerTaskTest {

    @Test
    void testActivePlayerParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/activeplayer.json");
        final String player = "BooonE";
        ActivePlayer mockedActivePlayer = mock(ActivePlayer.class);
        when(mockedActivePlayer.getSummonerName()).thenReturn(player);
        GameState.setActivePlayer(mockedActivePlayer);

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