package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class RespawnAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playRespawnAnimation() {
        final ChampionStats mockedChampionStats = GameStateMocks.mockChampionStats();
        when(mockedChampionStats.getResourceMax()).thenReturn(100.0);
        when(mockedChampionStats.getResourceValue()).thenReturn(100.0);
        when(mockedChampionStats.getCurrentHealth()).thenReturn(100.0);
        when(mockedChampionStats.getMaxHealth()).thenReturn(100.0);

        GameStateMocks.mockActivePlayerChampionName("Morgana");
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}