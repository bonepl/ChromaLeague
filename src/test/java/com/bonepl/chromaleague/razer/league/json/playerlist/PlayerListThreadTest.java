package com.bonepl.chromaleague.razer.league.json.playerlist;

import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.playerlist.model.PlayerList;
import com.bonepl.chromaleague.razer.league.json.playerlist.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerListThreadTest {
    private PlayerListThread playerListThread;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/playerlist.json").toURI()).toPath());
        LeagueHttpClient mockedLeagueHttpClient = mock(LeagueHttpClient.class);
        when(mockedLeagueHttpClient.fetchJson(any())).thenReturn(testJson);

        playerListThread = new PlayerListThread(mockedLeagueHttpClient);
    }

    @Test
    void testPlayerListParsing() {
        //when
        final PlayerList playerList = new PlayerList("BooonE", playerListThread.fetchData());

        //then
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals("BooonE", playerList.getActivePlayer().getSummonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().getTeam());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
    }
}