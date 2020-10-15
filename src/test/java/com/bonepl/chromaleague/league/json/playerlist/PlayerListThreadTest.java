package com.bonepl.chromaleague.league.json.playerlist;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.json.playerlist.model.Player;
import com.bonepl.chromaleague.league.json.playerlist.model.PlayerList;
import com.bonepl.chromaleague.league.json.playerlist.model.Team;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class PlayerListThreadTest {

    @Test
    void testPlayerListParsing() throws URISyntaxException, IOException {
        //given
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/playerlist.json").toURI()).toPath());
        GameState.setActivePlayerName("BooonE");

        //when
        final PlayerList playerList = new PlayerList(JsonIterator.deserialize(testJson, Player[].class));

        //then
        assertEquals(5, playerList.getAllies().size());
        assertEquals(5, playerList.getEnemies().size());
        assertEquals("BooonE", playerList.getActivePlayer().getSummonerName());
        assertEquals(Team.CHAOS, playerList.getActivePlayer().getTeam());
        assertTrue(playerList.isAlly("Test summoner 5"));
        assertFalse(playerList.isAlly("Test summoner 9"));
    }
}