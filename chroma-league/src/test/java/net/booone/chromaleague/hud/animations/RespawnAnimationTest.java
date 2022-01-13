package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.rest.activeplayer.ChampionStats;
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

        when(gameStateMocks.player().championName()).thenReturn("Morgana");

        // then
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}