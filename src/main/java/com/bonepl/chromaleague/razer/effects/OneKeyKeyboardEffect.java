package com.bonepl.chromaleague.razer.effects;

import com.bonepl.chromaleague.razer.sdk.RzKey;

public class OneKeyKeyboardEffect extends CustomKeyboardEffect {

    public OneKeyKeyboardEffect(RzKey rzKey, Color color) {
        colors[rzKey.getCustomPosition()] = color.getSDKColorRef();
    }

}
