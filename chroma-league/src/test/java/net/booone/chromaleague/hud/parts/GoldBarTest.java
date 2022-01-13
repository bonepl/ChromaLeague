package net.booone.chromaleague.hud.parts;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.state.GameStateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class GoldBarTest {

    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        gameStateMocks = new GameStateMocks();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testGoldAnimation() {
        //given
        mockActivePlayerGold(0.0);
        final GoldBar goldBar = new GoldBar(10, 150);
        new AnimationTester()
                .withAfterIterationAction(i -> mockActivePlayerGold(GameStateHelper.getGold() + 30))
                .testAnimation(goldBar, 500);
    }

    private void mockActivePlayerGold(double gold) {
        when(gameStateMocks.activePlayer().currentGold()).thenReturn(gold);
    }
}