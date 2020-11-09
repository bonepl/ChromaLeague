package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.mockResource;

class RumbleHeatBarTest {

    @Test
    void testRumbleStandardHeat() {
        final IntSteps heatSteps = new IntSteps(0, 100, 5);
        final IntSteps cooldownSteps = new IntSteps(100, 0, 2);

        final RumbleHeatBar rumbleHeatBar = new RumbleHeatBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i < 20) {
                        ManaBarTest.mockResource(heatSteps.nextInt(), 100);
                    } else {
                        ManaBarTest.mockResource(cooldownSteps.nextInt(), 100);
                    }
                })
                .withSleepTime(300)
                .testAnimation(rumbleHeatBar, 67);
    }

    @Test
    void testRumbleOverheatHeat() {
        final IntSteps heatSteps = new IntSteps(0, 100, 5);

        final RumbleHeatBar rumbleHeatBar = new RumbleHeatBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    mockResource(heatSteps.nextInt(), 100);
                })
                .withSleepTime(300)
                .testAnimation(rumbleHeatBar, 40);
    }
}