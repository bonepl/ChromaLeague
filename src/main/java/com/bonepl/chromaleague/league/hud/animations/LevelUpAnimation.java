package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.GameStateHelper;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.effects.animation.Animation;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.effects.animation.LayeredFrame;

import java.util.Arrays;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

public class LevelUpAnimation extends Animation {
    private int previousLevel = 0;

    @Override
    public LayeredFrame getNextAnimatedFrame() {
        addLevelUpAnimationIfNeeded();
        return super.getNextAnimatedFrame();
    }

    private void addLevelUpAnimationIfNeeded() {
        if (GameStateHelper.getLevel() > previousLevel) {
            levelUp();
            previousLevel = GameStateHelper.getLevel();
        }
    }

    private void levelUp() {
        final AnimatedFrame frame = new AnimatedFrame()
                .withAnimationFrame(3, new Frame(RZKEY_MACRO5, Color.YELLOW))
                .withAnimationFrame(3, new Frame(Arrays.asList(RZKEY_MACRO5, RZKEY_MACRO4), Color.YELLOW))
                .withAnimationFrame(3, new Frame(Arrays.asList(RZKEY_MACRO5, RZKEY_MACRO4, RZKEY_MACRO3), Color.YELLOW))
                .withAnimationFrame(3, new Frame(Arrays.asList(RZKEY_MACRO4, RZKEY_MACRO3, RZKEY_MACRO2), Color.YELLOW))
                .withAnimationFrame(3, new Frame(Arrays.asList(RZKEY_MACRO3, RZKEY_MACRO2, RZKEY_MACRO1), Color.YELLOW))
                .withAnimationFrame(3, new Frame(Arrays.asList(RZKEY_MACRO2, RZKEY_MACRO1), Color.YELLOW))
                .withAnimationFrame(3, new Frame(RZKEY_MACRO1, Color.YELLOW));
        addToFront(frame);
    }
}
