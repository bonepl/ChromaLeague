package com.bonepl.razersdk.sdk.json.request;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.sdk.RzKey2;

import java.util.Arrays;
import java.util.Map;

public class KeyboardEffect {
    String effect = "CHROMA_CUSTOM";
    int[][] param = new int[6][22];

    public KeyboardEffect() {
        for (int[] rows : param) {
            Arrays.fill(rows, Color.NONE.getSDKColorRef());
        }
    }

    public KeyboardEffect(Map<RzKey2, Color> keysToColors) {
        keysToColors.forEach((key, value) -> param[key.getRow()][key.getColumn()] = value.getSDKColorRef());
    }
}
