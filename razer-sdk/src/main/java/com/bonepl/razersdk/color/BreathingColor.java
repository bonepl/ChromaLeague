package com.bonepl.razersdk.color;

/**
 * Dynamic Color that continuously provides
 * Color being the result of transition between provided upColor and downColor
 */
public class BreathingColor implements Color {
    private final TransitionColor upColor;
    private final TransitionColor downColor;
    private boolean upDirection;

    public BreathingColor(Color upColor, Color downColor) {
        this(upColor, downColor, 20);
    }

    public BreathingColor(Color upColor, Color downColor, int steps) {
        this(upColor, downColor, steps, false);
    }

    public BreathingColor(Color upColor, Color downColor, int steps, boolean startUpDirection) {
        upDirection = startUpDirection;
        this.upColor = new TransitionColor(downColor, upColor, steps);
        this.downColor = new TransitionColor(upColor, downColor, steps);
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
}
