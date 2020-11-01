package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class VladimirBloodPoolBarTest {

    @Test
    void testVladimirPoolBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final VladimirBloodPoolBar vladimirBloodPoolBar = new VladimirBloodPoolBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(vladimirBloodPoolBar, 40);

    }

    private static void mockResource(double resource, double maxResource) {
        final ChampionStats mockedChampionStats = GameStateMocks.getMockedChampionStats();
        when(mockedChampionStats.getResourceValue()).thenReturn(resource);
        when(mockedChampionStats.getResourceMax()).thenReturn(maxResource);
    }
}