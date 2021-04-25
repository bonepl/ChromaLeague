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
        final Color color = new Color(from.red() - getRedStep() * currentStep,
                from.green() - getGreenStep() * currentStep,
                from.blue() - getBlueStep() * currentStep);
        if (transitionFinished()) {
            return to;
        }
        currentStep += 1;
        return color;
    }

    public Color getColorAtPercent(int percent) {
        return new Color((int) (from.red() - (from.red() - to.red()) * percent * 0.01),
                (int) (from.green() - (from.green() - to.green()) * percent * 0.01),
                (int) (from.blue() - (from.blue() - to.blue()) * percent * 0.01));
    }

    private int getRedStep() {
        return (from.red() - to.red()) / steps;
    }

    private int getGreenStep() {
        return (from.green() - to.green()) / steps;
    }

    private int getBlueStep() {
        return (from.blue() - to.blue()) / steps;
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
        if (newSteps != steps) {
            final double stepsRatio = (double) currentStep / steps;
            currentStep = (int) (stepsRatio * newSteps);
            steps = newSteps;
        }
    }

    public Color getFrom() {
        return from;
    }

    public Color getTo() {
        return to;
    }
}
