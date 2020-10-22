package com.bonepl.chromaleague.hud;

import com.bonepl.razersdk.animation.Color;

public class BreathingColor {
    private final TransitionColor upColor;
    private final TransitionColor downColor;
    private boolean upDirection = true;

    public BreathingColor(Color color) {
        this(color, 20);
    }

    public BreathingColor(Color color, int steps) {
        this.upColor = new TransitionColor(Color.BLACK, color, steps / 2);
        this.downColor = new TransitionColor(color, Color.BLACK, steps / 2);
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
