package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class LevelUpBar extends Animation {
    private static final List<RzKey> LEVEL_UP_KEYS = List.of(
            RZKEY_MACRO5, RZKEY_LCTRL, RZKEY_LWIN,
            RZKEY_MACRO4, RZKEY_LSHIFT,
            RZKEY_MACRO3, RZKEY_CAPSLOCK,
            RZKEY_MACRO2, RZKEY_TAB,
            RZKEY_MACRO1, RZKEY_TILDE);
    private int previousLevel;

    @Override
    public Frame getFrame() {
        addLevelUpAnimationIfNeeded();
        if (hasFrame()) {
            return super.getFrame();
        }
        return new SimpleFrame().getFrame();
    }

    private void addLevelUpAnimationIfNeeded() {
        if (GameStateHelper.getLevel() > previousLevel) {
            levelUp();
            previousLevel = GameStateHelper.getLevel();
        }
    }

    void levelUp() {
        final AnimatedFrame frame = new AnimatedFrame();
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(0, 3), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(0, 5), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(0, 7), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(3, 9), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(5, 11), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(7, 11), StaticColor.YELLOW));
        frame.addAnimationFrame(2, new SimpleFrame(LEVEL_UP_KEYS.subList(9, 11), StaticColor.YELLOW));
        addToFront(frame);
    }
}
