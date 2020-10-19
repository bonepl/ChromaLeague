package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;

public class StaticBlinkingAnimation extends AnimatedFrame {

    public StaticBlinkingAnimation(int times, Color color) {
        for (int i = 0; i < times; i++) {
            withAnimationFrame(new Frame(color));
            withAnimationFrame(3, new Frame(Color.BLACK));
        }
    }
}
