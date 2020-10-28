package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class HpBarAnimationTest {

    @Test
    void testHpBarLostAnimation() {
        final HpBarAnimation hpBarAnimation = new HpBarAnimation();
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
                .testAnimation(hpBarAnimation, 60);
    }

    @Test
    void testHpBarGainedAnimation() {
        final HpBarAnimation hpBarAnimation = new HpBarAnimation();
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
                .testAnimation(hpBarAnimation, 60);
    }
}