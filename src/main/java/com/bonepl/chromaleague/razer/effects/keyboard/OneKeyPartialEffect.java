package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Collections;

public class OneKeyPartialEffect extends StaticPartialEffect {

    public OneKeyPartialEffect(RzKey rzKey, Color color) {
        super(Collections.singletonList(rzKey), color);
    }
}
