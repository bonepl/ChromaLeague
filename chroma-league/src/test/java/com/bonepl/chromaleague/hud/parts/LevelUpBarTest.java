package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class LevelUpBarTest {

    @Test
    void playLevelUpAnimation() {
        // given
        final ActivePlayer activePlayer = new GameStateMocks("BooonE").activePlayer();
        when(activePlayer.getLevel()).thenReturn(1);

        // when
        final LevelUpBar levelUpBar = new LevelUpBar();
        levelUpBar.levelUp();

        // then
        new AnimationTester().testAnimation(levelUpBar);
    }
}