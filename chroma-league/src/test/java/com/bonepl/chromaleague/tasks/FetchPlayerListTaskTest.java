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

    public static final String PLAYER_NAME = "BooonE";

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
        GameStateMocks.mockActivePlayer("BooonE");
        //given

        //when
        new FetchPlayerListTask().run();

        //then
        assertTrue(RunningState.getGameState().isPlayerListAvailable());
        final PlayerList playerList = RunningState.getGameState().getPlayerList();
        assertNotNull(playerList);
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals(PLAYER_NAME, playerList.getActivePlayer().getSummonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().getTeam());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
        assertTrue(playerList.getEnemies().stream().anyMatch("Łążćkiewicz"::equals));

        final Player activePlayer = RunningState.getGameState().getPlayerList().getActivePlayer();
        assertEquals("Cho'Gath", activePlayer.getChampionName());
        assertEquals("BooonE", activePlayer.getSummonerName());
        assertEquals(Team.CHAOS, activePlayer.getTeam());
    }

    @Test
    void testDependencyOnActivePlayer() {
        //when
        RunningState.setRunningGame(true);
        new FetchPlayerListTask().run();

        //then
        assertFalse(RunningState.getGameState().isPlayerListAvailable());
    }
}