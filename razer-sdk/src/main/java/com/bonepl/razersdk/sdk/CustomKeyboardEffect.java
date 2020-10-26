package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.animation.Color;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.*;

/**
 * Razer Chroma SDK C++ class describing illumination of keys
 */
public class CustomKeyboardEffect extends Structure {
    public static final int ROW_COUNT = 6;
    public static final int COLUMN_COUNT = 22;
    @SuppressWarnings("PublicField")
    public int[] colors = new int[ROW_COUNT * COLUMN_COUNT];

    public CustomKeyboardEffect() {
        Arrays.fill(colors, Color.NONE.getSDKColorRef());
    }

    public CustomKeyboardEffect(final Map<RzKey2, Color> keysToColors) {
        this();
//        keysToColors.forEach((rzKey, color) -> colors[rzKey.getCustomPosition()] = color.getSDKColorRef());
    }

    public Pointer getEffect() {
        write();
        return getPointer();
    }

    @Override
    public List<String> getFieldOrder() {
        return Collections.singletonList("colors");
    }
}
