package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.hud.animations.BaronBuffBackground;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.effects.animation.LayeredFrame;

public class Background extends LayeredFrame {
    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10);
    private static final AnimatedFrame baronBuffBackground = new BaronBuffBackground();

    public Background() {
        withFrame(new Frame(BACKGROUND_COLOR));
        if (GameState.hasBaronBuff()) {
            withFrame(baronBuffBackground.getFrame());
        }
    }
}
