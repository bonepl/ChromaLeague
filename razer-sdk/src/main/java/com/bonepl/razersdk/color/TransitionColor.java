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
        if (steps < 2) {
            throw new IllegalArgumentException("TransitionColor needs at least 2 steps for transition, provided steps: " + steps);
        }
        this.steps = steps;
    }

    public StaticColor getColorAtPercent(int percent) {
        return new StaticColor((int) (from.red() - (from.red() - to.red()) * percent * 0.01),
                (int) (from.green() - (from.green() - to.green()) * percent * 0.01),
                (int) (from.blue() - (from.blue() - to.blue()) * percent * 0.01));
    }

    private int getRedStep() {
        return (from.red() - to.red()) / (steps - 1);
    }

    private int getGreenStep() {
        return (from.green() - to.green()) / (steps - 1);
    }

    private int getBlueStep() {
        return (from.blue() - to.blue()) / (steps - 1);
    }

    public boolean transitionFinished() {
        return currentStep == steps;
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
        if (transitionFinished()) {
            return to;
        }

        if(currentStep + 1 == steps){
            currentStep += 1;
            return to;
        }

        final StaticColor color = new StaticColor(from.red() - getRedStep() * currentStep,
                from.green() - getGreenStep() * currentStep,
                from.blue() - getBlueStep() * currentStep);
        currentStep += 1;
        return color;
    }
}
