package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.rest.gamestats.MapTerrain;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.mockito.Mockito.when;

class RiftAnimationTest {

    @ParameterizedTest
    @EnumSource(MapTerrain.class)
    void playRiftAnimation(MapTerrain mapTerrain) {
        // given
        GameStats gameStats = new GameStateMocks().gameStats();
        when(gameStats.mapTerrain()).thenReturn(mapTerrain.getApiType());

        // then
        new AnimationTester().testAnimation(new RiftAnimation());
    }
}