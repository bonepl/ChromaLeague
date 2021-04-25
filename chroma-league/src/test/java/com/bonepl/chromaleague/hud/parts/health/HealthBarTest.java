package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class HealthBarTest {

    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        gameStateMocks = new GameStateMocks();
    }

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

    private void mockActivePlayerHealth(double currentHealth, double maxHealth) {
        final ChampionStats mockedChampionStats = gameStateMocks.championStats();
        when(mockedChampionStats.currentHealth()).thenReturn(currentHealth);
        when(mockedChampionStats.maxHealth()).thenReturn(maxHealth);
    }
}