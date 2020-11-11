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
        // given
        GameStateMocks gameStateMocks = new GameStateMocks();

        final ChampionStats championStats = gameStateMocks.championStats();
        when(championStats.getResourceMax()).thenReturn(100.0);
        when(championStats.getResourceValue()).thenReturn(100.0);
        when(championStats.getCurrentHealth()).thenReturn(100.0);
        when(championStats.getMaxHealth()).thenReturn(100.0);

        when(gameStateMocks.player().getChampionName()).thenReturn("Morgana");

        // then
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}