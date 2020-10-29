package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.animation.Color;

public class TransitionColor {
    private static final int DEFAULT_STEPS = 20;
    private Color from;
    private Color to;
    private int steps;
    private int currentStep;

    public TransitionColor(Color from, Color to) {
        this(from, to, DEFAULT_STEPS);
    }

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
        }
        currentStep += 1;
        return color;
    }

    public Color getColorAtPercent(int percent) {
        return new Color((int) (from.getRed() - ((from.getRed() - to.getRed()) * percent * 0.01)),
                (int) (from.getGreen() - ((from.getGreen() - to.getGreen()) * percent * 0.01)),
                (int) (from.getBlue() - ((from.getBlue() - to.getBlue()) * percent * 0.01)));
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

    public void setFrom(Color from) {
        this.from = from;
    }

    public void setTo(Color to) {
        this.to = to;
    }

    public void setSteps(int newSteps) {
        final double stepsRatio = (double) currentStep / steps;
        currentStep = (int) (stepsRatio * newSteps);
        steps = newSteps;
    }

    public Color getFrom() {
        return from;
    }

    public Color getTo() {
        return to;
    }
}
