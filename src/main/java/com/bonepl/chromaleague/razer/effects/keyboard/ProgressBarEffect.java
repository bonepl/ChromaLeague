package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class ProgressBarEffect extends AnimatedFrame {

    public ProgressBarEffect(final List<RzKey> progressBar, final Integer percent, final Color color) {
        withAnimationFrame(getFrame(progressBar, percent, color));
    }

    private static Frame getFrame(final List<RzKey> progressBar, final Integer percent, final Color color) {
        int filledBar = percent >= 100 ? progressBar.size() : (int) (progressBar.size() * (percent * 0.01));
        return new Frame(progressBar.subList(0, filledBar), color);
    }
}
