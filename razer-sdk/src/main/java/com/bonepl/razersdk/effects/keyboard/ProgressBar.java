package com.bonepl.razersdk.effects.keyboard;

import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class ProgressBar extends Frame {

    public ProgressBar(final List<RzKey> progressBar, final Integer percent, final Color color) {
        super(getBarPercent(progressBar, percent), color);
    }

    private static List<RzKey> getBarPercent(final List<RzKey> progressBar, final Integer percent) {
        return progressBar.subList(0, indexToFill(progressBar, percent));
    }

    private static int indexToFill(List<RzKey> progressBar, Integer percent) {
        return percent >= 100 ? progressBar.size() : (int) (progressBar.size() * (percent * 0.01));
    }
}
