package com.bonepl.chromaleague.league.rest.playerlist;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.league.rest.playerlist.model.PlayerList;
import com.bonepl.chromaleague.league.rest.playerlist.model.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchPlayerListTaskTest {

    @Test
    void testPlayerListParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/playerlist.json");
        GameState.setActivePlayerName("BooonE");

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