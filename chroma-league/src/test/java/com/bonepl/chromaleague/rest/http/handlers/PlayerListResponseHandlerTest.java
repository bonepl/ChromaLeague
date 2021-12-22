package com.bonepl.chromaleague.rest.http.handlers;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.rest.playerlist.Team;
import com.bonepl.chromaleague.state.RunningState;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.bonepl.chromaleague.GameStateMocks.PLAYER_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerListResponseHandlerTest {

    @Test
    void testPlayerListParsing() throws IOException, URISyntaxException {
        //given
        RunningState.getGameState().setPlayerName(PLAYER_NAME);
        HttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/playerlist.json");

        //when
        Optional<PlayerList> playerList = new PlayerListResponseHandler().handleResponse(testResponseFromJSON);

        //then
        assertTrue(playerList.isPresent());
        verifyPlayerList(playerList.get());
    }

    public static void verifyPlayerList(PlayerList playerList) {
        assertNotNull(playerList);
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals("BooonE", playerList.getActivePlayer().summonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().team());
        assertEquals(4.5, playerList.getActivePlayer().respawnTimer());
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