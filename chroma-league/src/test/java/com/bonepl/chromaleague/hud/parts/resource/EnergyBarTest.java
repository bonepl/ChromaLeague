package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class EnergyBarTest {
    @Test
    void testEnergyBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final EnergyBar energyBar = new EnergyBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> ManaBarTest.mockResource(intSteps.nextInt(), 100))
                .testAnimation(energyBar, 40);
    }
}