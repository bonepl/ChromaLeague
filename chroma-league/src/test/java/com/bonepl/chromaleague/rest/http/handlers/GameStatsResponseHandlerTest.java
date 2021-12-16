package com.bonepl.chromaleague.rest.http.handlers;

import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatsResponseHandlerTest {

    @Test
    void testGameStatsParsing() throws IOException, URISyntaxException {
        //given
        HttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/gamestats.json");

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