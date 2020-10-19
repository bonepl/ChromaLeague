package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.hud.PredefinedKeySets;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelUpAnimation extends Animation {
    private final List<RzKey> LEVEL_UP_KEYS;
    private int previousLevel = 0;

    public LevelUpAnimation() {
        LEVEL_UP_KEYS = new ArrayList<>(PredefinedKeySets.MACROS);
        Collections.reverse(LEVEL_UP_KEYS);
    }

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
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.get(0), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 1), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 2), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(0, 3), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(1, 4), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(2, 4), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.subList(3, 4), Color.YELLOW))
                .withAnimationFrame(2, new Frame(LEVEL_UP_KEYS.get(4), Color.YELLOW));
        addToFront(frame);
    }
}
