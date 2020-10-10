package com.bonepl.chromaleague.razer.effects;

import com.bonepl.chromaleague.razer.sdk.RzKeyboardEffectType;
import com.sun.jna.Pointer;

public interface SDKKeyboardEffect {
    RzKeyboardEffectType getSDKKeyboardEffectType();

    Pointer getEffect();
}
