package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.razersdk.animation.Color;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FIRST_ROW;

class ProgressBarTest {
    private static final int STEP = 5;
    private static int PERCENT = 0;
    private static int UP = 1;

    @Test
    void testProgressBarEffect() {
        new AnimationTester()
                .testAnimation(() -> new ProgressBar(BLACKWIDOW_FIRST_ROW, nextPercent(), Color.GREEN), 40);
    }

    private static int nextPercent() {
        if (PERCENT + STEP > 100 || PERCENT + STEP < 0) {
            UP = Math.negateExact(UP);
        }
        PERCENT += STEP * UP;
        return PERCENT;
    }
}