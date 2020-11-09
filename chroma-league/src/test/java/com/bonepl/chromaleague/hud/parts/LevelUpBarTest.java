package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class LevelUpBarTest {

    @Test
    void playLevelUpAnimation() {
        final ActivePlayer activePlayer = GameStateMocks.mockActivePlayer("BooonE");
        when(activePlayer.getLevel()).thenReturn(1);

        final LevelUpBar levelUpBar = new LevelUpBar();
        levelUpBar.levelUp();

        new AnimationTester().testAnimation(levelUpBar);
    }
}