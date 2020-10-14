package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class PartialStaticEffect extends CustomEffect {

    public PartialStaticEffect(List<RzKey> keys, Color color) {
        keys.forEach(rzKey -> colors[rzKey.getCustomPosition()] = color.getSDKColorRef());
    }
}
