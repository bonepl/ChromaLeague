package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class StaticPartialBlinkingAnimation extends AnimatedFrame {

    public StaticPartialBlinkingAnimation(List<RzKey> rzKeys, int times, Color color) {
        for (int i = 0; i < times; i++) {
            addAnimationFrame(new SimpleFrame(rzKeys, color));
            addAnimationFrame(3, new SimpleFrame(rzKeys, Color.BLACK));
        }
    }
}
