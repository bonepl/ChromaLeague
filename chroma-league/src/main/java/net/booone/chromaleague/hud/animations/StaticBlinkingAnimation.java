package net.booone.chromaleague.hud.animations;

import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

public abstract class StaticBlinkingAnimation extends AnimatedFrame {
    private static final int BLINK_TIMES = 8;

    protected StaticBlinkingAnimation(Color color) {
        for (int i = 0; i < BLINK_TIMES; i++) {
            addAnimationFrame(new SimpleFrame(color));
            addAnimationFrame(3, new SimpleFrame(StaticColor.BLACK));
        }
    }
}
