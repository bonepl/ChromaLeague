package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.hud.parts.Background;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class StaticPartialBlinkingAnimation extends AnimatedFrame {

    public StaticPartialBlinkingAnimation(List<RzKey> rzKeys, int times, Color color) {
        for (int i = 0; i < times; i++) {
            withAnimationFrame(new Frame(rzKeys, color));
            withAnimationFrame(3, new Frame(rzKeys, Background.BACKGROUND_COLOR));
        }
    }
}
