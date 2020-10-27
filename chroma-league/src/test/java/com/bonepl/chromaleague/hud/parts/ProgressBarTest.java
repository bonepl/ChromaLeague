package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.razersdk.animation.Color;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FIRST_ROW;

class ProgressBarTest {
    private static final int STEP = 5;
    private static int percent;
    private static int direction = 1;

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testProgressBarEffect() {
        new AnimationTester()
                .testAnimation(() -> new ProgressBar(BLACKWIDOW_FIRST_ROW, nextPercent(), Color.GREEN), 40);
    }

    private static int nextPercent() {
        if (percent + STEP > 100 || percent + STEP < 0) {
            direction = Math.negateExact(direction);
        }
        percent += STEP * direction;
        return percent;
    }
}