package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.CustomKeyboardEffect;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Frame {
    private final Map<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);

    public Frame(Map<RzKey, Color> keysToColors) {
        this.keysToColors.putAll(keysToColors);
    }

    public Map<RzKey, Color> getKeysToColors() {
        return Collections.unmodifiableMap(keysToColors);
    }

    public CustomKeyboardEffect toCustomEffect() {
        return new CustomKeyboardEffect(getKeysToColors());
    }
}
