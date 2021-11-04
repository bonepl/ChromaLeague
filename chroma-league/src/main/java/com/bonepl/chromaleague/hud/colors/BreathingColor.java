package com.bonepl.chromaleague.hud.colors;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

public class BreathingColor implements Color {
    private final TransitionColor upColor;
    private final TransitionColor downColor;
    private boolean upDirection;

    public BreathingColor(Color color) {
        this(color, 20);
    }

    public BreathingColor(Color color, int steps) {
        this(color, steps, false);
    }

    public BreathingColor(Color color, int steps, boolean startUpDirection) {
        this(color, Background.BACKGROUND_COLOR, steps, startUpDirection);
    }

    public BreathingColor(Color upColor, Color downColor, int steps, boolean startUpDirection) {
        upDirection = startUpDirection;
        this.upColor = new TransitionColor(downColor, upColor, steps);
        this.downColor = new TransitionColor(upColor, downColor, steps);
    }

    public StaticColor getNextColor() {
        if (upDirection) {
            StaticColor color = upColor.getColor();
            if (upColor.transitionFinished()) {
                upDirection = false;
                upColor.resetTransition();
            }
            return color;
        }
        StaticColor color = downColor.getColor();
        if (downColor.transitionFinished()) {
            upDirection = true;
            downColor.resetTransition();
        }
        return color;
    }

    public void setSteps(int steps) {
        upColor.setSteps(steps);
        downColor.setSteps(steps);
    }

    public void setUpColor(Color color) {
        upColor.setTo(color);
        downColor.setFrom(color);
    }

    public void setDownColor(Color color) {
        upColor.setFrom(color);
        downColor.setTo(color);
    }

    @Override
    public StaticColor getColor() {
        return getNextColor();
    }
}
