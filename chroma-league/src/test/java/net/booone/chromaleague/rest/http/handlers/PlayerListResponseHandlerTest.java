package net.booone.chromaleague.rest.http.handlers;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.rest.playerlist.Player;
import net.booone.chromaleague.rest.playerlist.PlayerList;
import net.booone.chromaleague.rest.playerlist.Team;
import net.booone.chromaleague.state.RunningState;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static net.booone.chromaleague.GameStateMocks.PLAYER_RIOT_ID;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerListResponseHandlerTest {

    @Test
    void testPlayerListParsing() throws IOException, URISyntaxException {
        //given
        RunningState.getGameState().setPlayerRiotId(PLAYER_RIOT_ID);
        ClassicHttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/playerlist.json");

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
        assertEquals("BonE#EUPL", playerList.getActivePlayer().riotId());
        assertEquals("BonE", playerList.getActivePlayer().riotIdGameName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().team());
        assertEquals(4.5, playerList.getActivePlayer().respawnTimer());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
        assertTrue(playerList.getEnemies().stream().anyMatch("Łążćkiewicz"::equals));

        final Player activePlayer = playerList.getActivePlayer();
        assertEquals("Cho'Gath", activePlayer.championName());
        assertEquals("BonE#EUPL", activePlayer.riotId());
        assertEquals("BonE", activePlayer.riotIdGameName());
        assertTrue(activePlayer.isDead());
        assertEquals(Team.CHAOS, activePlayer.team());
    }
}