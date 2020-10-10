package com.bonepl.chromaleague.razer.sdk.effects;

import com.sun.jna.Pointer;

public interface SDKKeyboardEffect {
    int getSDKKeyboardEffectType();

    Pointer getEffect();
}
