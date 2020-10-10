package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.sdk.RzKeyboardEffectType;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public class StaticEffectSDK extends Structure implements SDKKeyboardEffect {
    public int color;

    public StaticEffectSDK(Color color1) {
        this.color = color1.getSDKColorRef();
        this.write();
    }

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("color");
    }

    @Override
    public RzKeyboardEffectType getSDKKeyboardEffectType() {
        return RzKeyboardEffectType.STATIC;
    }

    @Override
    public Pointer getEffect() {
        return this.getPointer();
    }
}
