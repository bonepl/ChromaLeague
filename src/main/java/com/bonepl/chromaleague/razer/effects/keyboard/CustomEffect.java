package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzCustomKeyboardEffect;

import java.util.Arrays;

public abstract class CustomEffect extends RzCustomKeyboardEffect {
    public CustomEffect() {
        Arrays.fill(colors, Color.BLACK.getSDKColorRef());
    }
}
