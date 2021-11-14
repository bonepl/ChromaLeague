package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.BaronBuffBackgroundAnimation;
import com.bonepl.chromaleague.hud.colors.BackgroundBreathingColor;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;

public class Background extends LayeredFrame {
    public static final StaticColor BACKGROUND_COLOR = new StaticColor(5, 5, 5);
    private static final AnimatedFrame BARON_BUFF_BACKGROUND_ANIMATION = new BaronBuffBackgroundAnimation();
    private static final BreathingColor DEAD_BACKGROUND = new BackgroundBreathingColor(new StaticColor(60, 40, 40));

    public Background() {
        if (GameStateHelper.isActivePlayerAlive()) {
            addFrame(new SimpleFrame(BACKGROUND_COLOR));
        } else {
            addFrame(new SimpleFrame(DEAD_BACKGROUND.getColor()));
        }
        if (GameStateHelper.hasBaronBuff()) {
            addFrame(BARON_BUFF_BACKGROUND_ANIMATION);
        }
    }
}
