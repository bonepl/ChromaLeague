package com.bonepl.razersdk.effects.keyboard;

import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.sdk.RzCustomKeyboardEffect;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.EnumMap;

public class CustomEffect extends RzCustomKeyboardEffect {
    public CustomEffect() {
        Arrays.fill(colors, Color.BLACK.getSDKColorRef());
    }

    public CustomEffect(final EnumMap<RzKey, Color> keysToColors) {
        this();
        keysToColors.forEach((r, c) -> colors[r.getCustomPosition()] = c.getSDKColorRef());
    }
}
