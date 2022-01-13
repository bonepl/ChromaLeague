package net.booone.chromaleague.hud.parts;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.hud.PredefinedKeySets;
import net.booone.razersdk.color.StaticColor;
import org.junit.jupiter.api.Test;

class ProgressBarTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testProgressBarEffect() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        new AnimationTester()
                .testAnimation(() -> new ProgressBar(PredefinedKeySets.BLACKWIDOW_FIRST_ROW, intSteps.nextInt(), StaticColor.GREEN), 40);
    }
}