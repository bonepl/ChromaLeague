package com.bonepl.razersdk.color;

/**
 * Dynamic Color that continuously provides
 * Color being the result of transition between provided fromColor and toColor.
 * Once the transition finishes - toColor is returned
 */
public class TransitionColor implements Color {
    private static final int DEFAULT_STEPS = 20;
    private StaticColor from;
    private StaticColor to;
    private int steps;
    private int currentStep;

    public TransitionColor(StaticColor from, StaticColor to) {
        this(from, to, DEFAULT_STEPS);
    }

    public TransitionColor(StaticColor from, StaticColor to, int steps) {
        this.from = from;
        this.to = to;
        this.steps = steps;
    }

    public StaticColor getColorAtPercent(int percent) {
        return new StaticColor((int) (from.red() - (from.red() - to.red()) * percent * 0.01),
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

    public void setFrom(StaticColor from) {
        this.from = from;
    }

    public void setTo(StaticColor to) {
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
        final StaticColor color = new StaticColor(from.red() - getRedStep() * currentStep,
                from.green() - getGreenStep() * currentStep,
                from.blue() - getBlueStep() * currentStep);
        if (transitionFinished()) {
            return to;
        }
        currentStep += 1;
        return color;
    }
}
