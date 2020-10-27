package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.rest.playerlist.Team;
import com.bonepl.chromaleague.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FetchPlayerListTaskTest {

    public static final String PLAYER_NAME = "BooonE";

    @BeforeEach
    void setUp() {
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/playerlist.json");
        GameState.setActivePlayer(null);
        GameState.setPlayerList(null);
    }

    @Test
    void testPlayerListParsing() {
        //given
        GameState.setActivePlayer(createActivePlayerMock());

        //when
        new FetchPlayerListTask().run();

        //then
        assertTrue(GameState.isPlayerListAvailable());
        final PlayerList playerList = GameState.getPlayerList();
        assertNotNull(playerList);
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals(PLAYER_NAME, playerList.getActivePlayer().getSummonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().getTeam());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));

        final Player activePlayer = GameState.getPlayerList().getActivePlayer();
        assertEquals("Cho'Gath", activePlayer.getChampionName());
        assertEquals("BooonE", activePlayer.getSummonerName());
        assertEquals(Team.CHAOS, activePlayer.getTeam());
    }

    @Test
    void testDependencyOnActivePlayer() {
        //when
        new FetchPlayerListTask().run();

        //then
        assertFalse(GameState.isPlayerListAvailable());
    }

    private static ActivePlayer createActivePlayerMock() {
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getSummonerName()).thenReturn(PLAYER_NAME);
        return activePlayer;
    }

}