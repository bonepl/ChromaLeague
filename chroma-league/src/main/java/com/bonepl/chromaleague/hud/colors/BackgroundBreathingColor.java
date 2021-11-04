package com.bonepl.chromaleague.hud.colors;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.razersdk.color.StaticColor;

public class BackgroundBreathingColor extends BreathingColor {

    public BackgroundBreathingColor(StaticColor upColor) {
        super(upColor, Background.BACKGROUND_COLOR);
    }

    public BackgroundBreathingColor(StaticColor upColor, int steps) {
        super(upColor, Background.BACKGROUND_COLOR, steps);
    }

    public BackgroundBreathingColor(StaticColor upColor, int steps, boolean startUpDirection) {
        super(upColor, Background.BACKGROUND_COLOR, steps, startUpDirection);
    }
}
