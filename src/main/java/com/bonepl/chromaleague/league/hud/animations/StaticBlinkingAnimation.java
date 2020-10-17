package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.hud.parts.Background;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.effects.animation.Frame;

public class StaticBlinkingAnimation extends AnimatedFrame {

    public StaticBlinkingAnimation(int times, Color color) {
        for (int i = 0; i < times; i++) {
            withAnimationFrame(new Frame(color));
            withAnimationFrame(3, new Background());
        }
    }
}
