package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyCloudDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyCloudDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyCloudDragonKillAnimation());
    }
}