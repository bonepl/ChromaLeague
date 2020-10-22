package com.bonepl.chromaleague.hud;

import com.bonepl.razersdk.animation.Color;

public class TransitionColor {
    private final Color from;
    private final Color to;
    private final int steps;
    private int currentStep = 0;

    public TransitionColor(Color from, Color to, int steps) {
        this.from = from;
        this.to = to;
        this.steps = steps;
    }

    public Color getNextColor() {
        final Color color = new Color(from.getRed() - getRedStep() * currentStep,
                from.getGreen() - getGreenStep() * currentStep,
                from.getBlue() - getBlueStep() * currentStep);
        if (transitionFinished()) {
            return to;
        } else {
            currentStep = currentStep + 1;
        }
        return color;
    }

    private int getRedStep() {
        return (from.getRed() - to.getRed()) / steps;
    }

    private int getGreenStep() {
        return (from.getGreen() - to.getGreen()) / steps;
    }

    private int getBlueStep() {
        return (from.getBlue() - to.getBlue()) / steps;
    }

    public boolean transitionFinished() {
        return currentStep + 1 == steps;
    }

    public void resetTransition() {
        currentStep = 0;
    }
}
