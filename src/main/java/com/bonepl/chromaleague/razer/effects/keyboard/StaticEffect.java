package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;

import java.util.Arrays;

public class StaticEffect extends CustomEffect {

    public StaticEffect(Color color) {
        Arrays.fill(colors, color.getSDKColorRef());
    }
}
