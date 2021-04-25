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
        when(championStats.resourceMax()).thenReturn(100.0);
        when(championStats.resourceValue()).thenReturn(100.0);
        when(championStats.currentHealth()).thenReturn(100.0);
        when(championStats.maxHealth()).thenReturn(100.0);

        when(gameStateMocks.player().getChampionName()).thenReturn("Morgana");

        // then
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}