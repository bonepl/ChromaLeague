package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.BaronBuffBackgroundAnimation;
import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;

public class Background extends LayeredFrame {
    public static final Color BACKGROUND_COLOR = new Color(5, 5, 5);
    private static final AnimatedFrame baronBuffBackground = new BaronBuffBackgroundAnimation();
    private static final BreathingColor deadBackground = new BreathingColor(new Color(60, 40, 40));

    public Background() {
        if (GameStateHelper.isActivePlayerAlive()) {
            addFrame(new SimpleFrame(BACKGROUND_COLOR));
        } else {
            addFrame(new SimpleFrame(deadBackground.getNextColor()));
        }
        if (GameStateHelper.hasBaronBuff()) {
            addFrame(baronBuffBackground);
        }
    }
}
