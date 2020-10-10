package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.sdk.RzKeyboardEffectType;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CustomKeyboardEffect extends Structure implements SDKKeyboardEffect {
    public static final int ROW_COUNT = 6;
    public static final int COLUMN_COUNT = 22;
    public int[] colors = new int[ROW_COUNT * COLUMN_COUNT];

    public CustomKeyboardEffect() {
        Arrays.fill(colors, Color.BLACK.getSDKColorRef());
    }

    @Override
    public RzKeyboardEffectType getSDKKeyboardEffectType() {
        return RzKeyboardEffectType.CUSTOM;
    }

    @Override
    public Pointer getEffect() {
        this.write();
        return this.getPointer();
    }


    @Override
    public List<String> getFieldOrder() {
        return Collections.singletonList("colors");
    }
}
