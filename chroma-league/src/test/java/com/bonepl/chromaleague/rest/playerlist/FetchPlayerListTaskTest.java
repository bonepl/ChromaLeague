package com.bonepl.chromaleague.rest.playerlist;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.tasks.FetchPlayerListTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FetchPlayerListTaskTest {

    @Test
    void testPlayerListParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/playerlist.json");
        final String player = "BooonE";
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getSummonerName()).thenReturn(player);
        GameState.setActivePlayer(activePlayer);

        //when
        new FetchPlayerListTask().run();

        //then
        assertTrue(GameState.isPlayerListAvailable());
        final PlayerList playerList = GameState.getPlayerList();
        assertNotNull(playerList);
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals("BooonE", playerList.getActivePlayer().getSummonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().getTeam());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
    }
}