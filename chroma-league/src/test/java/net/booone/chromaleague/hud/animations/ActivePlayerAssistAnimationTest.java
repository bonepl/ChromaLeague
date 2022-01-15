package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerAssistAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playActivePlayerAssistAnimation() {
        //given
        new GameStateMocks().mockTestPlayerList();
        //then
        new AnimationTester().testAnimation(new ActivePlayerAssistAnimation());
    }
}