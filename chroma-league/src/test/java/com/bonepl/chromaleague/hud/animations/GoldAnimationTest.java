package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.rest.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.model.ChampionStats;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoldAnimationTest {

    @Test
    void testGoldAnimation() throws InterruptedException {
        final GoldAnimation goldAnimation = new GoldAnimation(10, 150);
        mockActiveGold(0.0);
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 100; i++) {
                razerSDKClient.createKeyboardEffect(goldAnimation.getFrame());
                Thread.sleep(50);
                mockActiveGold(GameStateHelper.getGold() + 30);
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