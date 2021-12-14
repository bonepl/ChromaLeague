package com.bonepl.chromaleague.rest.gamestats;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.FetchGameStatsTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapTerrainTest {
    private LeagueHttpClientMock leagueHttpClientMock;

    @BeforeEach
    void setUp() {
        leagueHttpClientMock = new LeagueHttpClientMock();
    }

    @ParameterizedTest
    @MethodSource("gameStatsToMapTerrains")
    public void shouldParseMapTerrain(String json, MapTerrain expectedMapTerrain) {
        //given
        leagueHttpClientMock.mockGameStatsResponse("json/gamestats/" + json);

        //when
        new FetchGameStatsTask().run();

        //then
        String mapTerrain = RunningState.getGameState().getGameStats().mapTerrain();
        assertEquals(expectedMapTerrain, MapTerrain.fromApiType(mapTerrain));
    }

    private static Stream<Arguments> gameStatsToMapTerrains() {
        return Stream.of(
                Arguments.of("chemtechMapTerrain.json", MapTerrain.CHEMTECH),
                Arguments.of("cloudMapTerrain.json", MapTerrain.CLOUD),
                Arguments.of("hextechMapTerrain.json", MapTerrain.HEXTECH),
                Arguments.of("infernalMapTerrain.json", MapTerrain.INFERNAL),
                Arguments.of("mountainMapTerrain.json", MapTerrain.MOUNTAIN),
                Arguments.of("oceanMapTerrain.json", MapTerrain.OCEAN)
        );
    }
}