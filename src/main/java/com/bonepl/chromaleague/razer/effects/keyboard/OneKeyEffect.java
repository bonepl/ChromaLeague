package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

public class OneKeyEffect extends CustomEffect {

    public OneKeyEffect(RzKey rzKey, Color color) {
        colors[rzKey.getCustomPosition()] = color.getSDKColorRef();
    }

}
