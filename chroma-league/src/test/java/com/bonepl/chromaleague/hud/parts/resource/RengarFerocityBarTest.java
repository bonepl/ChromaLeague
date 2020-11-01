package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.mockResource;

class RengarFerocityBarTest {
    @Test
    void testRengarFerocityBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 25);
        final RengarFerocityBar rengarFerocityBar = new RengarFerocityBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i > 20) {
                        mockResource(100, 100);
                    } else if (i % 5 == 0) {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .testAnimation(rengarFerocityBar, 40);

    }
}