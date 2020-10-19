package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Frame;

public class StaticBlinkingAnimation extends AnimatedFrame {

    public StaticBlinkingAnimation(int times, Color color) {
        for (int i = 0; i < times; i++) {
            withAnimationFrame(new Frame(color));
            withAnimationFrame(3, new Frame(Color.BLACK));
        }
    }
}
