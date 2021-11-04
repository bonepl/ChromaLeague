package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.razersdk.color.Color;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FIRST_ROW;

class ProgressBarTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testProgressBarEffect() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        new AnimationTester()
                .testAnimation(() -> new ProgressBar(BLACKWIDOW_FIRST_ROW, intSteps.nextInt(), Color.GREEN), 40);
    }
}