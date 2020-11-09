package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class ManaBarTest {
    @Test
    void testManaBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final ManaBar manaBar = new ManaBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(manaBar, 40);
    }

    public static void mockResource(double resource, double maxResource) {
        final ChampionStats mockedChampionStats = GameStateMocks.mockChampionStats();
        when(mockedChampionStats.getResourceValue()).thenReturn(resource);
        when(mockedChampionStats.getResourceMax()).thenReturn(maxResource);
    }
}