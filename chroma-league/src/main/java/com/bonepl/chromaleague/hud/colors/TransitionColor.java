package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

public class TransitionColor implements Color {
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
        final Color color = new StaticColor(from.getColor().red() - getRedStep() * currentStep,
                from.getColor().green() - getGreenStep() * currentStep,
                from.getColor().blue() - getBlueStep() * currentStep);
        if (transitionFinished()) {
            return to;
        }
        currentStep += 1;
        return color;
    }

    public Color getColorAtPercent(int percent) {
        return new StaticColor((int) (from.getColor().red() - (from.getColor().red() - to.getColor().red()) * percent * 0.01),
                (int) (from.getColor().green() - (from.getColor().green() - to.getColor().green()) * percent * 0.01),
                (int) (from.getColor().blue() - (from.getColor().blue() - to.getColor().blue()) * percent * 0.01));
    }

    private int getRedStep() {
        return (from.getColor().red() - to.getColor().red()) / steps;
    }

    private int getGreenStep() {
        return (from.getColor().green() - to.getColor().green()) / steps;
    }

    private int getBlueStep() {
        return (from.getColor().blue() - to.getColor().blue()) / steps;
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

    @Override
    public StaticColor getColor() {
        return getNextColor().getColor();
    }
}
