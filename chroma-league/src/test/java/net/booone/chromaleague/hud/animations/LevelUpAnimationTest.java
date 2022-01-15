package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class LevelUpAnimationTest {

    @Test
    void playLevelUpAnimation() {
        // given
        when(new GameStateMocks().activePlayer().level()).thenReturn(1);

        // when
        final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
        levelUpAnimation.levelUp();

        // then
        new AnimationTester().testAnimation(levelUpAnimation);
    }
}