package com.bonepl.chromaleague.league.json.activeplayer;

import com.bonepl.chromaleague.league.json.LeagueHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivePlayerThreadTest {

    private ActivePlayerThread activePlayerThread;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/activeplayer.json").toURI()).toPath());
        LeagueHttpClient mockedLeagueHttpClient = mock(LeagueHttpClient.class);
        when(mockedLeagueHttpClient.fetchData(any())).thenReturn(testJson);

        activePlayerThread = new ActivePlayerThread(mockedLeagueHttpClient);
    }

    @Test
    void testActivePlayerParsing() {
        //when
        activePlayerThread.fetchAndUpdateData();

        //then
        assertEquals(100, activePlayerThread.getHpPercentage());
        assertEquals(50, activePlayerThread.getResourcePercentage());
    }

    @Test
    void testActivePlayerParsingNoData() {
        assertEquals(0, activePlayerThread.getHpPercentage());
        assertEquals(0, activePlayerThread.getResourcePercentage());
    }
}