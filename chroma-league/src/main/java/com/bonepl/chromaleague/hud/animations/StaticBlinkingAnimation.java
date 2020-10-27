package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;

public abstract class StaticBlinkingAnimation extends AnimatedFrame {

    protected StaticBlinkingAnimation(int times, Color color) {
        for (int i = 0; i < times; i++) {
            addAnimationFrame(new SimpleFrame(color));
            addAnimationFrame(3, new SimpleFrame(Color.BLACK));
        }
    }
}
