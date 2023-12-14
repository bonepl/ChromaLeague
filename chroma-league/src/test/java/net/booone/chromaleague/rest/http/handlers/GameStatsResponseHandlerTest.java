package net.booone.chromaleague.rest.http.handlers;

import net.booone.chromaleague.rest.gamestats.GameStats;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatsResponseHandlerTest {

    @Test
    void testGameStatsParsing() throws IOException, URISyntaxException, HttpException {
        //given
        ClassicHttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/gamestats.json");

        //when
        Optional<GameStats> gameStats = new GameStatsResponseHandler().handleResponse(testResponseFromJSON);

        //then
        assertTrue(gameStats.isPresent());
        verifyGameStats(gameStats.get());
    }

    public static void verifyGameStats(GameStats gameStats) {
        assertNotNull(gameStats);
        assertEquals("CLASSIC", gameStats.gameMode());
        assertEquals(15.123456789, gameStats.gameTime());
        assertEquals("Infernal", gameStats.mapTerrain());
    }
}