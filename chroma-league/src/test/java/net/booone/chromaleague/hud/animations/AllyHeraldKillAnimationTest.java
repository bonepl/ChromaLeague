package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyHeraldKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyHeraldKillAnimation() {
        new AnimationTester().testAnimation(new AllyHeraldKillAnimation());
    }
}