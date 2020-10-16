package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.CustomEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FramePart extends CustomEffect {

    public FramePart(RzKey rzKey, Color color) {
        this(new EnumMap<>(Collections.singletonMap(rzKey, color)));
    }

    public FramePart(List<RzKey> rzKeys, Color color) {
        this(rzKeys.stream().collect(Collectors.toMap(Function.identity(), r -> color, (x, y) -> x, () -> new EnumMap<>(RzKey.class))));
    }

    public FramePart(EnumMap<RzKey, Color> keysToColors) {
        keysToColors.forEach((rzKey, color) -> colors[rzKey.getCustomPosition()] = color.getSDKColorRef());
    }
}
