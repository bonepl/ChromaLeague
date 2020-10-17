package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class ProgressBarEffect extends Frame {

    public ProgressBarEffect(final List<RzKey> progressBar, final Integer percent, final Color color) {
        super(getBarPercent(progressBar, percent), color);
    }

    private static List<RzKey> getBarPercent(final List<RzKey> progressBar, final Integer percent) {
        return progressBar.subList(0, indexToFill(progressBar, percent));
    }

    private static int indexToFill(List<RzKey> progressBar, Integer percent) {
        return percent >= 100 ? progressBar.size() : (int) (progressBar.size() * (percent * 0.01));
    }
}
