package com.bonepl.razersdk.color;

/**
 * Dynamic Color that continuously provides
 * Color being the result of transition between provided fromColor and toColor.
 * Once the transition finishes - toColor is returned
 */
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

    public Color getColorAtPercent(int percent) {
        StaticColor nextFromColor = from.getColor();
        StaticColor nextToColor = to.getColor();
        return new StaticColor((int) (nextFromColor.red() - (nextFromColor.red() - nextToColor.red()) * percent * 0.01),
                (int) (nextFromColor.green() - (nextFromColor.green() - nextToColor.green()) * percent * 0.01),
                (int) (nextFromColor.blue() - (nextFromColor.blue() - nextToColor.blue()) * percent * 0.01));
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
        StaticColor nextFromColor = from.getColor();
        final Color color = new StaticColor(nextFromColor.red() - getRedStep() * currentStep,
                nextFromColor.green() - getGreenStep() * currentStep,
                nextFromColor.blue() - getBlueStep() * currentStep);
        if (transitionFinished()) {
            return to.getColor();
        }
        currentStep += 1;
        return color.getColor();
    }
}
