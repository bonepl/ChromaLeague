package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FetchRespawnTimeTest {
    @BeforeEach
    void setUp() {
        new LeagueHttpClientMock().mockPlayerListResponse("json/playerlist.json");
    }

    @Test
    void testRespawnTimeFetch() {
        //given
        new GameStateMocks();

        //when
        final double respawnTime = FetchRespawnTime.fetchPlayerRespawnTime();

        //then
        assertEquals(4.5, respawnTime);
    }
}