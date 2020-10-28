package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class HpBarTest {

    @Test
    void testHpBarLostAnimation() {
        final HpBar hpBar = new HpBar();
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
                .testAnimation(hpBar, 60);
    }

    @Test
    void testHpBarGainedAnimation() {
        final HpBar hpBar = new HpBar();
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
                .testAnimation(hpBar, 60);
    }
}