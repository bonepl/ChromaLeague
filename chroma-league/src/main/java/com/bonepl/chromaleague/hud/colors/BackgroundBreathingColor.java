package com.bonepl.chromaleague.hud.colors;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.razersdk.color.Color;

public class BackgroundBreathingColor extends BreathingColor {

    public BackgroundBreathingColor(Color upColor) {
        super(upColor, Background.BACKGROUND_COLOR);
    }

    public BackgroundBreathingColor(Color upColor, int steps) {
        super(upColor, Background.BACKGROUND_COLOR, steps);
    }

    public BackgroundBreathingColor(Color upColor, int steps, boolean startUpDirection) {
        super(upColor, Background.BACKGROUND_COLOR, steps, startUpDirection);
    }
}
