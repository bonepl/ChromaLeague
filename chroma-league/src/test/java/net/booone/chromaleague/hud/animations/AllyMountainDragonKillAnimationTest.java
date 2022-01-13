package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyMountainDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyMountainDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyMountainDragonKillAnimation());
    }
}