package net.booone.razersdk.color;

/**
 * Dynamic Color that continuously provides
 * Color being the result of transition between provided upColor and downColor
 */
public class BreathingColor implements Color {
    private final TransitionColor upColor;
    private final TransitionColor downColor;
    private boolean upDirection;

    public BreathingColor(StaticColor upColor, StaticColor downColor) {
        this(upColor, downColor, 20);
    }

    public BreathingColor(StaticColor upColor, StaticColor downColor, int steps) {
        this(upColor, downColor, steps, false);
    }

    public BreathingColor(StaticColor upColor, StaticColor downColor, int steps, boolean startUpDirection) {
        this(new TransitionColor(downColor, upColor, steps), new TransitionColor(upColor, downColor, steps), startUpDirection);
    }

    public BreathingColor(TransitionColor upColor, TransitionColor downColor) {
        this(upColor, downColor, false);
    }

    public BreathingColor(TransitionColor upColor, TransitionColor downColor, boolean startUpDirection) {
        upDirection = startUpDirection;
        this.upColor = upColor;
        this.downColor = downColor;
    }

    public void setSteps(int steps) {
        upColor.setSteps(steps);
        downColor.setSteps(steps);
    }

    public void setUpColor(StaticColor color) {
        upColor.setTo(color);
        downColor.setFrom(color);
    }

    public void setDownColor(StaticColor color) {
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
