package com.bonepl.chromaleague.league.rest.activeplayer;

import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

class ActivePlayerThreadTest {

    @Test
    void testActivePlayerParsing() throws URISyntaxException, IOException {
        //given
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/activeplayer.json").toURI()).toPath());

        //when
        final ActivePlayer activePlayer = JsonIterator.deserialize(testJson, ActivePlayer.class);

        //then
//        assertEquals(100, activePlayerThread.getHpPercentage());
//        assertEquals(50, activePlayerThread.getResourcePercentage());
    }

    @Test
    void testActivePlayerParsingNoData() {
//        assertEquals(0, activePlayerThread.getHpPercentage());
//        assertEquals(0, activePlayerThread.getResourcePercentage());
    }
}