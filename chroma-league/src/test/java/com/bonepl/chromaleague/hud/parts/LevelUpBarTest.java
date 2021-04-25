package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class LevelUpBarTest {

    @Test
    void playLevelUpAnimation() {
        // given
        when(new GameStateMocks().activePlayer().level()).thenReturn(1);

        // when
        final LevelUpBar levelUpBar = new LevelUpBar();
        levelUpBar.levelUp();

        // then
        new AnimationTester().testAnimation(levelUpBar);
    }
}