package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.state.GameStateHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.mockActivePlayerGold;

class GoldBarTest {
    @AfterAll
    static void afterAll() {
        GameStateMocks.clearActivePlayer();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testGoldAnimation() {
        //given
        mockActivePlayerGold(0.0);
        final GoldBar goldBar = new GoldBar(10, 150);
        new AnimationTester()
                .withAfterIterationAction(i -> mockActivePlayerGold(GameStateHelper.getGold() + 30))
                .testAnimation(goldBar, 100);
    }
}