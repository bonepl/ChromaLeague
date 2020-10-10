package com.bonepl.chromaleague.razer.effects;

import java.util.Arrays;

public class StaticKeyboardEffect extends CustomKeyboardEffect {

    public StaticKeyboardEffect(Color color) {
        Arrays.fill(colors, color.getSDKColorRef());
    }
}
