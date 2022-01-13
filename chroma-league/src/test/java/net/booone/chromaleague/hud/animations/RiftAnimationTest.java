package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.rest.gamestats.GameStats;
import net.booone.chromaleague.rest.gamestats.MapTerrain;
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