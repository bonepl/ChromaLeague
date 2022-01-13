package net.booone.chromaleague.hud.parts;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
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