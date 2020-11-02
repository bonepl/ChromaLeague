package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class HealthBarTest {

    @Test
    void testHpBarLostAnimation() {
        final HealthBar healthBar = new HealthBar();
        GameStateMocks.mockActivePlayerHealth(1000, 1000);

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0) {
                        final int curHp = 1000 - i * 20;
                        if (curHp >= 0) {
                            GameStateMocks.mockActivePlayerHealth(curHp, 1000);
                        }
                    }
                })
                .withSleepTime(100)
                .testAnimation(healthBar, 60);
    }

    @Test
    void testHpBarGainedAnimation() {
        final HealthBar healthBar = new HealthBar();
        GameStateMocks.mockActivePlayerHealth(0, 1000);

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0) {
                        final int curHp = i * 20;
                        if (curHp <= 1000) {
                            GameStateMocks.mockActivePlayerHealth(curHp, 1000);
                        }
                    }
                })
                .withSleepTime(100)
                .testAnimation(healthBar, 60);
    }
}