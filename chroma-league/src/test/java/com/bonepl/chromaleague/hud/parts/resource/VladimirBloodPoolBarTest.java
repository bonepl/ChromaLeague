package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class VladimirBloodPoolBarTest extends AbstractResourceTest {

    @Test
    void testVladimirPoolBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final VladimirBloodPoolBar vladimirBloodPoolBar = new VladimirBloodPoolBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 30) {
                        mockResource(100, 100);
                    } else {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .testAnimation(vladimirBloodPoolBar, 50);

    }
}