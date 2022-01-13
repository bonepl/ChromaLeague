package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.rest.eventdata.DragonType;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AllyDragonKillBlinkingAnimation extends AnimatedFrame {

    private static final int BLINK_TIMES = 2;
    private static final int COLOR_LENGTH = 80;

    public AllyDragonKillBlinkingAnimation(DragonType dragonType) {
        this(new RzKeySelector().all().asList(), dragonType);
    }

    protected AllyDragonKillBlinkingAnimation(List<RzKey> rzKeys, DragonType dragonType) {
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
