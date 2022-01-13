package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.color.TransitionColor;

public class GnarFuryBar extends AnimatedFrame {
    private final TransitionColor fromYellowToRed = new TransitionColor(StaticColor.YELLOW, StaticColor.RED);
    private final BreathingColor aboutToTransform = new BackgroundBreathingColor(StaticColor.YELLOW, 20, false);

    private static final int COLOR_TRANSITION_PERCENT_START = 50;
    private static final int COLOR_TRANSITION_PERCENT_STEP = 100 / COLOR_TRANSITION_PERCENT_START;

    @Override
    public Frame getFrame() {
        final double activePlayerRange = GameStateHelper.getActivePlayerRange();
        final int gnarFuryPercent = GameStateHelper.getResourcePercentage();
        if (activePlayerRange < 300) {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, StaticColor.RED));
        } else {
            if (gnarFuryPercent < COLOR_TRANSITION_PERCENT_START) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, StaticColor.YELLOW));
            } else if (gnarFuryPercent < 85) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent,
                        fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START))));
            } else if (gnarFuryPercent < 100) {
                aboutToTransform.setUpColor(fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START)));
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent,
                        aboutToTransform.getColor()));
            } else {
                aboutToTransform.setUpColor(StaticColor.RED);
                aboutToTransform.setSteps(5);
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, aboutToTransform.getColor()));
            }
        }
        return super.getFrame();
    }
}
