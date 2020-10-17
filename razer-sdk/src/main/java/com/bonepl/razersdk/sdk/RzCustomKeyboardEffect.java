package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.effects.keyboard.SDKKeyboardEffect;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public abstract class RzCustomKeyboardEffect extends Structure implements SDKKeyboardEffect {
    public static final int ROW_COUNT = 6;
    public static final int COLUMN_COUNT = 22;
    public int[] colors = new int[ROW_COUNT * COLUMN_COUNT];

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
