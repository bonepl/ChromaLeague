package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.CustomKeyboardEffect;
import com.bonepl.razersdk.sdk.RzKey2;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Frame object containing map of keys to corresponding colors
 */
public class Frame {
    private final Map<RzKey2, Color> keysToColors = new EnumMap<>(RzKey2.class);

    /**
     * Create a frame from provided keys to colors map
     *
     * @param keysToColors map of keys to colors
     */
    public Frame(Map<RzKey2, Color> keysToColors) {
        this.keysToColors.putAll(keysToColors);
    }

    /**
     * Return the underlying map of keys to colors
     *
     * @return map of keys to colors
     */
    public Map<RzKey2, Color> getKeysToColors() {
        return Collections.unmodifiableMap(keysToColors);
    }

    /**
     * Transform this class to {@link CustomKeyboardEffect} C++ object required for creating animation effect
     *
     * @return Razer Chroma SDK C++ {@link CustomKeyboardEffect} class
     */
    public CustomKeyboardEffect toCustomEffect() {
        return new CustomKeyboardEffect(getKeysToColors());
    }
}
