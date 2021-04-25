package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.rest.playerlist.Team;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchPlayerListTaskTest {

    @BeforeEach
    void setUp() {
        new LeagueHttpClientMock().mockPlayerListResponse("json/playerlist.json");
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testPlayerListParsing() {
        //given
        new GameStateMocks();

        //when
        new FetchPlayerListTask().run();
        final PlayerList playerList = RunningState.getGameState().getPlayerList();

        //then
        assertNotNull(playerList);
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals("BooonE", playerList.getActivePlayer().summonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().team());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
        assertTrue(playerList.getEnemies().stream().anyMatch("Łążćkiewicz"::equals));

        final Player activePlayer = playerList.getActivePlayer();
        assertEquals("Cho'Gath", activePlayer.championName());
        assertEquals("BooonE", activePlayer.summonerName());
        assertTrue(activePlayer.isDead());
        assertEquals(Team.CHAOS, activePlayer.team());
    }
}