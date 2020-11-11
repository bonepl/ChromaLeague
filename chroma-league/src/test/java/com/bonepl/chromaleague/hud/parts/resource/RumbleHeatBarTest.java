package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class RumbleHeatBarTest extends AbstractResourceTest {

    @Test
    void testRumbleStandardHeat() {
        final IntSteps heatSteps = new IntSteps(0, 100, 5);
        final IntSteps cooldownSteps = new IntSteps(100, 0, 2);

        final RumbleHeatBar rumbleHeatBar = new RumbleHeatBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i < 20) {
                        mockResource(heatSteps.nextInt(), 100);
                    } else {
                        mockResource(cooldownSteps.nextInt(), 100);
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