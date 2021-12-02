package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class RiftAnimationTest {
    @Test
    void playRiftAnimation() {
        // given
        GameStats gameStats = new GameStateMocks().gameStats();
        when(gameStats.mapTerrain()).thenReturn("Infernal");

        // then
        new AnimationTester().testAnimation(new RiftAnimation());
    }
}