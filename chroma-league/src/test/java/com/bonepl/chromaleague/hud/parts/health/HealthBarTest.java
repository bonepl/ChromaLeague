package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class HealthBarTest {

    @Test
    void testHpBarLostAnimation() {
        final HealthBar healthBar = new HealthBar();
        mockActivePlayerHealth(1000, 1000);

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0) {
                        final int currentHealth = 1000 - i * 20;
                        if (currentHealth >= 0) {
                            mockActivePlayerHealth(currentHealth, 1000);
                        }
                    }
                })
                .withSleepTime(100)
                .testAnimation(healthBar, 60);
    }

    @Test
    void testHpBarGainedAnimation() {
        final HealthBar healthBar = new HealthBar();
        mockActivePlayerHealth(0, 1000);

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0) {
                        final int currentHealth = i * 20;
                        if (currentHealth <= 1000) {
                            mockActivePlayerHealth(currentHealth, 1000);
                        }
                    }
                })
                .withSleepTime(100)
                .testAnimation(healthBar, 60);
    }

    private static void mockActivePlayerHealth(double currentHealth, double maxHealth) {
        final ChampionStats mockedChampionStats = GameStateMocks.mockChampionStats();
        when(mockedChampionStats.getCurrentHealth()).thenReturn(currentHealth);
        when(mockedChampionStats.getMaxHealth()).thenReturn(maxHealth);
    }
}