package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Animation;
import com.bonepl.razersdk.effects.animation.Frame;

import java.util.Arrays;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class LevelUpAnimation extends Animation {
    private int previousLevel = 0;

    @Override
    public Frame getFrame() {
        addLevelUpAnimationIfNeeded();
        return super.getFrame();
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
