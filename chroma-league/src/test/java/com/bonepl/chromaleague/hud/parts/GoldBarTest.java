package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoldBarTest {

    @Test
    void testGoldAnimation() throws InterruptedException {
        final GoldBar goldBar = new GoldBar(10, 150);
        mockActiveGold(0.0);
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 100; i++) {
                razerSDKClient.createKeyboardEffect(goldBar);
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