package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;

public class GnarFuryBar extends AnimatedFrame {
    private final TransitionColor fromYellowToRed = new TransitionColor(Color.YELLOW, Color.RED);
    private final BreathingColor aboutToTransform = new BreathingColor(Color.YELLOW, Background.BACKGROUND_COLOR, 20, false);

    private static final int COLOR_TRANSITION_PERCENT_START = 50;
    private static final int COLOR_TRANSITION_PERCENT_STEP = 100 / COLOR_TRANSITION_PERCENT_START;

    @Override
    public Frame getFrame() {
        final double activePlayerRange = GameStateHelper.getActivePlayerRange();
        final int gnarFuryPercent = GameStateHelper.getResourcePercentage();
        if (activePlayerRange < 300) {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, Color.RED));
        } else {
            if (gnarFuryPercent < COLOR_TRANSITION_PERCENT_START) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, Color.YELLOW));
            } else if (gnarFuryPercent < 85) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent,
                        fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START))));
            } else if (gnarFuryPercent < 100) {
                aboutToTransform.setUpColor(fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START)));
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent,
                        aboutToTransform.getNextColor()));
            } else {
                aboutToTransform.setUpColor(Color.RED);
                aboutToTransform.setSteps(5);
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), gnarFuryPercent, aboutToTransform.getNextColor()));
            }
        }
        return super.getFrame();
    }
}
