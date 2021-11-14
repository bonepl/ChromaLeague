package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DragonKillBlinkingAnimation extends AnimatedFrame {

    private static final int BLINK_TIMES = 2;
    private static final int COLOR_LENGTH = 80;

    public DragonKillBlinkingAnimation(DragonType dragonType) {
        this(new RzKeySelector().all().asList(), dragonType);
    }

    protected DragonKillBlinkingAnimation(List<RzKey> rzKeys, DragonType dragonType) {
        addBlinkingColor(rzKeys, dragonType);
        addDynamicColor(rzKeys, dragonType);
        addBlinkingColor(rzKeys, dragonType);
    }

    private void addDynamicColor(List<RzKey> rzKeys, DragonType dragonType) {
        Map<RzKey, Color> colorsMap = rzKeys.stream()
                .collect(Collectors.toMap(Function.identity(), rk -> dragonType.getSoulColor()));
        for (int i = 0; i < COLOR_LENGTH; i++) {
            addAnimationFrame(new SimpleFrame(colorsMap));
        }
    }

    private void addBlinkingColor(List<RzKey> rzKeys, DragonType dragonType) {
        for (int i = 0; i < BLINK_TIMES; i++) {
            addAnimationFrame(new SimpleFrame(rzKeys, dragonType.getColor()));
            addAnimationFrame(3, new SimpleFrame(rzKeys, StaticColor.BLACK));
        }

    }
}
