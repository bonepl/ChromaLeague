package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.animation.Color;

public class BreathingColor {
    private final TransitionColor upColor;
    private final TransitionColor downColor;
    private boolean upDirection;

    public BreathingColor(Color color) {
        this(color, 20);
    }

    public BreathingColor(Color color, int steps) {
        this(color, steps, true);
    }

    public BreathingColor(Color color, int steps, boolean startUpDirection) {
        upDirection = startUpDirection;
        if (startUpDirection) {
            upColor = new TransitionColor(Color.BLACK, color, steps);
            downColor = new TransitionColor(color, Color.BLACK, steps);
        } else {
            downColor = new TransitionColor(color, Color.BLACK, steps);
            upColor = new TransitionColor(Color.BLACK, color, steps);
        }
    }

    public Color getNextColor() {
        if (upDirection) {
            Color color = upColor.getNextColor();
            if (upColor.transitionFinished()) {
                upDirection = false;
                upColor.resetTransition();
            }
            return color;
        }
        Color color = downColor.getNextColor();
        if (downColor.transitionFinished()) {
            upDirection = true;
            downColor.resetTransition();
        }
        return color;
    }
}
