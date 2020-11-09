package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.state.GameStateHelper;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class GoldBarTest {

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

    private static void mockActivePlayerGold(double gold) {
        final ActivePlayer activePlayer = GameStateMocks.mockActivePlayer("BooonE");
        when(activePlayer.getCurrentGold()).thenReturn(gold);
    }
}