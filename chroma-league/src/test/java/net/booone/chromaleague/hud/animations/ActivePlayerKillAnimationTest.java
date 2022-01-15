package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playActivePlayerKillAnimation() {
        //given
        new GameStateMocks().mockTestPlayerList();
        //then
        new AnimationTester().testAnimation(new ActivePlayerKillAnimation());
    }
}