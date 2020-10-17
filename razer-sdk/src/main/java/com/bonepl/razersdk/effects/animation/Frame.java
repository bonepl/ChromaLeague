package com.bonepl.razersdk.effects.animation;

import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.keyboard.CustomKeyboardEffect;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class Frame implements IFrame {
    private final EnumMap<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);
    private boolean hasFrame = true;

    public Frame() {
    }

    public Frame(Color color) {
        Arrays.stream(RzKey.values()).forEach(rzKey -> keysToColors.put(rzKey, color));
    }

    public Frame(RzKey rzKey, Color color) {
        keysToColors.put(rzKey, color);
    }

    public Frame(List<RzKey> rzKeys, Color color) {
        rzKeys.forEach(rzKey -> keysToColors.put(rzKey, color));
    }

    public Frame(EnumMap<RzKey, Color> keysToColors) {
        this.keysToColors.putAll(keysToColors);
    }

    public EnumMap<RzKey, Color> getKeysToColors() {
        return keysToColors;
    }

    public CustomKeyboardEffect toCustomEffect() {
        return new CustomKeyboardEffect(getKeysToColors());
    }

    @Override
    public boolean hasFrame() {
        return hasFrame;
    }

    @Override
    public Frame getFrame() {
        hasFrame = false;
        return this;
    }
}
