package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class StaticPartialBlinkingAnimation extends AnimatedFrame {

    public StaticPartialBlinkingAnimation(List<RzKey> rzKeys, int times, Color color) {
        for (int i = 0; i < times; i++) {
            withAnimationFrame(new Frame(rzKeys, color));
            withAnimationFrame(3, new Frame(rzKeys, Background.BACKGROUND_COLOR));
        }
    }
}
