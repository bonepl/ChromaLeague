package com.bonepl.chromaleague.razer.sdk;

public enum RzKeyboardEffectType {
    NONE(0),
    BREATHING(1),
    CUSTOM(2),
    REACTIVE(3),
    STATIC(4),
    SPECTRUMCYCLING(5),
    WAVE(6),
    RESERVED(7),
    CUSTOM_KEY(8),
    CUSTOM2(9),
    INVALID(10);

    private final int rzSDKKeyboardEffectType;

    RzKeyboardEffectType(int rzSDKKeyboardEffectType) {
        this.rzSDKKeyboardEffectType = rzSDKKeyboardEffectType;
    }

    public int getRzSDKKeyboardEffectType() {
        return rzSDKKeyboardEffectType;
    }
}
