package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeyJoiner;
import net.booone.razersdk.sdk.RzKeySelector;

import java.util.Set;

import static net.booone.razersdk.sdk.RzKey.*;

public class BaronBuffBackgroundAnimation extends AnimatedFrame {
    private final BreathingColor baronBuffColor = new BackgroundBreathingColor(new StaticColor(200, 0, 200));
    private static final Set<RzKey> BARON_AREA = buildBaronArea();

    @Override
    public Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            addAnimationFrame(2, new SimpleFrame(BARON_AREA, baronBuffColor.getColor()));
        }
    }

    public static Set<RzKey> buildBaronArea() {
        return new RzKeyJoiner()
                .with(new RzKeySelector().withRowOf(RZKEY_Q).withColumnBetween(RZKEY_Q, RZKEY_U))
                .with(new RzKeySelector().withRowOf(RZKEY_A).withColumnBetween(RZKEY_A, RZKEY_H))
                .with(new RzKeySelector().withRowOf(RZKEY_Z).withColumnBetween(RZKEY_Z, RZKEY_B))
                .join();
    }
}
