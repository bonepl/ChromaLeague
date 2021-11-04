package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.BaronBuffBackgroundAnimation;
import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;

public class Background extends LayeredFrame {
    public static final Color BACKGROUND_COLOR = new Color(5, 5, 5);
    private static final AnimatedFrame BARON_BUFF_BACKGROUND_ANIMATION = new BaronBuffBackgroundAnimation();
    private static final BreathingColor DEAD_BACKGROUND = new BreathingColor(new Color(60, 40, 40));

    public Background() {
        if (GameStateHelper.isActivePlayerAlive()) {
            addFrame(new SimpleFrame(BACKGROUND_COLOR));
        } else {
            addFrame(new SimpleFrame(DEAD_BACKGROUND.getNextColor()));
        }
        if (GameStateHelper.hasBaronBuff()) {
            addFrame(BARON_BUFF_BACKGROUND_ANIMATION);
        }
    }
}
