package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

class RiftAnimationTest {
    @Test
    void playRiftAnimation() {
        // given
        RunningState.getGameState().getEventData().setMapTerrain("Infernal");

        // then
        new AnimationTester().testAnimation(new RiftAnimation());
    }
}