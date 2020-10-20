package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class LevelUpAnimation extends Animation {
    private final List<RzKey> LEVEL_UP_KEYS = Arrays.asList(
            RZKEY_MACRO5, RZKEY_LCTRL, RZKEY_LWIN,
            RZKEY_MACRO4, RZKEY_LSHIFT,
            RZKEY_MACRO3, RZKEY_CAPSLOCK,
            RZKEY_MACRO2, RZKEY_TAB,
            RZKEY_MACRO1, RZKEY_OEM_1);
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

    void levelUp() {
        final AnimatedFrame frame = new AnimatedFrame()
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 3), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 5), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 7), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(3, 9), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(5, 11), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(7, 11), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(9, 11), Color.YELLOW));
        addToFront(frame);
    }
}
