package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class ProgressBarKeyboardEffect extends CustomKeyboardEffect {
    public ProgressBarKeyboardEffect(final List<RzKey> progressBar, final int percent, final Color color) {
        int filledBar = (int) (progressBar.size() * (percent * 0.01));
        progressBar.subList(0, filledBar)
                .forEach(rzKey -> colors[rzKey.getCustomPosition()] = color.getSDKColorRef());
    }
}
