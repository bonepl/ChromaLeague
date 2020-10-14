package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Collections;

public class OneKeyEffect extends PartialStaticEffect {

    public OneKeyEffect(RzKey rzKey, Color color) {
        super(Collections.singletonList(rzKey), color);
    }
}
