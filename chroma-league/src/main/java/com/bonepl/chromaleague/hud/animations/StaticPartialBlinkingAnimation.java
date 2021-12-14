package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

public abstract class StaticPartialBlinkingAnimation extends AnimatedFrame {
    private static final int BLINK_TIMES = 8;

    protected StaticPartialBlinkingAnimation(Color color) {
        for (int i = 0; i < BLINK_TIMES; i++) {
            addAnimationFrame(new SimpleFrame(com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL, color));
            addAnimationFrame(3, new SimpleFrame(com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }
}
