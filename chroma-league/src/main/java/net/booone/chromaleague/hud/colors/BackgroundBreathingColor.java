package net.booone.chromaleague.hud.colors;

import net.booone.chromaleague.hud.parts.Background;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.StaticColor;

public class BackgroundBreathingColor extends BreathingColor {

    public BackgroundBreathingColor(StaticColor upColor) {
        super(upColor, Background.DEFAULT_BACKGROUND_COLOR);
    }

    public BackgroundBreathingColor(StaticColor upColor, int steps) {
        super(upColor, Background.DEFAULT_BACKGROUND_COLOR, steps);
    }

    public BackgroundBreathingColor(StaticColor upColor, int steps, boolean startUpDirection) {
        super(upColor, Background.DEFAULT_BACKGROUND_COLOR, steps, startUpDirection);
    }
}
