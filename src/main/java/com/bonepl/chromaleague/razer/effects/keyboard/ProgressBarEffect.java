package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.FramePart;
import com.bonepl.chromaleague.razer.effects.animation.StaticFrame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class ProgressBarEffect extends StaticFrame {

    public ProgressBarEffect(final List<RzKey> progressBar, final Integer percent, final Color color) {
        super(getFrame(progressBar, percent, color));
    }

    private static FramePart getFrame(final List<RzKey> progressBar, final Integer percent, final Color color) {
        int filledBar = percent >= 100 ? progressBar.size() : (int) (progressBar.size() * (percent * 0.01));
        return new FramePart(progressBar.subList(0, filledBar), color);
    }
}
