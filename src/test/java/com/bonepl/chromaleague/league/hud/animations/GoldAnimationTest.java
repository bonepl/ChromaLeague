package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.GameStateHelper;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ChampionStats;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoldAnimationTest {
    @Test
    void testGoldAnimation() throws InterruptedException {
        final GoldAnimation goldAnimation = new GoldAnimation();
        mockActiveGold(0.0);
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 1000; i++) {
                razerSDKClient.createKeyboardEffect(goldAnimation.getNextFrame());
                Thread.sleep(1100);
                mockActiveGold(GameStateHelper.getGold() + 100);
            }
        }

    }

    private void mockActiveGold(double gold) {
        final ChampionStats csmock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(csmock);
        when(apMock.getCurrentGold()).thenReturn(gold);
        GameState.setActivePlayer(apMock);
    }
}