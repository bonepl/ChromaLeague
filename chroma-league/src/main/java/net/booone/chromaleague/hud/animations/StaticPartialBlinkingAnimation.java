package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.PredefinedKeySets;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

public abstract class StaticPartialBlinkingAnimation extends AnimatedFrame {
    private static final int BLINK_TIMES = 8;

    protected StaticPartialBlinkingAnimation(Color color) {
        for (int i = 0; i < BLINK_TIMES; i++) {
            addAnimationFrame(new SimpleFrame(PredefinedKeySets.BLACKWIDOW_FUNCTIONAL, color));
            addAnimationFrame(3, new SimpleFrame(PredefinedKeySets.BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }
}
