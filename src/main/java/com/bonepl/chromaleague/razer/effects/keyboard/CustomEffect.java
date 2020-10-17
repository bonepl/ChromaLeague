package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzCustomKeyboardEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;
import java.util.EnumMap;

public class CustomEffect extends RzCustomKeyboardEffect {
    public CustomEffect() {
        Arrays.fill(colors, Color.BLACK.getSDKColorRef());
    }

    public CustomEffect(final EnumMap<RzKey, Color> keysToColors) {
        keysToColors.forEach((r, c) -> colors[r.getCustomPosition()] = c.getSDKColorRef());
    }
}
