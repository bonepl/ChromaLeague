package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.mockResource;

class RumbleHeatBarTest {

    @Test
    void testRumbleStandardHeat() {
        final IntSteps intSteps = new IntSteps(0, 100, 3);
        final RumbleHeatBar rumbleHeatBar = new RumbleHeatBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    mockResource(intSteps.nextInt(), 100);
                })
                .withSleepTime(300)
                .testAnimation(rumbleHeatBar, 67);
    }

    @Test
    void testRumbleOverheatHeat() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final RumbleHeatBar rumbleHeatBar = new RumbleHeatBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    mockResource(intSteps.nextInt(), 100);
                })
                .withSleepTime(300)
                .testAnimation(rumbleHeatBar, 40);
    }
}