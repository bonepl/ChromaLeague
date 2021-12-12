package com.bonepl.chromaleague.rest.gamestats;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class MapTerrainTest {

    @ParameterizedTest
    public void dummy() {
    }

    private static Stream<Arguments> gameStatsToMapTerrains() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }
}