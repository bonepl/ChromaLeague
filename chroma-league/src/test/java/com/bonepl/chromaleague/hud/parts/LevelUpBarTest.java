package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class LevelUpBarTest {
    @Test
    void playLevelUpAnimation() {
        final LevelUpBar levelUpBar = new LevelUpBar();
        levelUpBar.levelUp();

        new AnimationTester().testAnimation(levelUpBar);
    }
}