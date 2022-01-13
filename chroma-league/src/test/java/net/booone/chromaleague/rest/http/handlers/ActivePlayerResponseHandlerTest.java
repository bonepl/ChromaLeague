package net.booone.chromaleague.rest.http.handlers;

import net.booone.chromaleague.rest.activeplayer.ActivePlayer;
import net.booone.chromaleague.rest.activeplayer.ChampionStats;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ActivePlayerResponseHandlerTest {
    @Test
    void testActivePlayerParsing() throws IOException, URISyntaxException {
        //given
        HttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/activeplayer.json");

        //when
        Optional<ActivePlayer> activePlayer = new ActivePlayerResponseHandler().handleResponse(testResponseFromJSON);

        //then
        assertTrue(activePlayer.isPresent());
        verifyActivePlayer(activePlayer.get());
    }

    public static void verifyActivePlayer(ActivePlayer activePlayer) {
        assertNotNull(activePlayer);
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