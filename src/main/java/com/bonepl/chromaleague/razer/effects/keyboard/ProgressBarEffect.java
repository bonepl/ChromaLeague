package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class ProgressBarEffect extends CustomEffect {

    public ProgressBarEffect(final List<RzKey> progressBar, final int percent, final Color color) {
        int filledBar = (int) (progressBar.size() * (percent * 0.01));
        progressBar.subList(0, filledBar)
                .forEach(rzKey -> colors[rzKey.getCustomPosition()] = color.getSDKColorRef());
    }
}
