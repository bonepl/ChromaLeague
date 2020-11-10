package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class RespawnAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playRespawnAnimation() {
        // given
        GameStateMocks gameStateMocks = new GameStateMocks();

        final ChampionStats mockChampionStats = gameStateMocks.championStats();
        when(mockChampionStats.getResourceMax()).thenReturn(100.0);
        when(mockChampionStats.getResourceValue()).thenReturn(100.0);
        when(mockChampionStats.getCurrentHealth()).thenReturn(100.0);
        when(mockChampionStats.getMaxHealth()).thenReturn(100.0);

        final PlayerList mockPlayerList = gameStateMocks.playerList();
        when(mockPlayerList.getActivePlayer().getChampionName()).thenReturn("Morgana");

        // then
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}