package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnergyBarTest extends AbstractResourceTest {
    @Test
    void testEnergyBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final EnergyBar energyBar = new EnergyBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(energyBar, 40);
    }
}