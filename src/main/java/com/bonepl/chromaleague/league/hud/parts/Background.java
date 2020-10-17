package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;

public class Background extends AnimatedFrame {
    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10);

    public Background() {
        withAnimationFrame(new Frame(Arrays.asList(RzKey.values()), BACKGROUND_COLOR));
    }
}
