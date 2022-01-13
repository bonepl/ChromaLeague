package net.booone.chromaleague.hud.parts.health;

import net.booone.chromaleague.hud.colors.CLColor;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.color.TransitionColor;
import net.booone.razersdk.sdk.RzKey;

import java.util.List;
import java.util.stream.IntStream;

import static net.booone.chromaleague.hud.parts.health.HealthBar.getHpBarPart;

public class GainedHealthAnimation extends AnimatedFrame {
    private static final int GAINED_HEALTH_CHANGE_STEPS = 5;

    public GainedHealthAnimation(double previousHp, double currentHp) {
        createGainedHealthAnimation(previousHp, currentHp);
    }

    private void createGainedHealthAnimation(double previousHp, double currentHp) {
        final List<RzKey> rzKeys = computeGainedHealth(previousHp, currentHp);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(StaticColor.WHITE, CLColor.HEALTH, GAINED_HEALTH_CHANGE_STEPS);
            IntStream.range(0, GAINED_HEALTH_CHANGE_STEPS).forEach(i -> addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getColor())));
        }
    }

    private static List<RzKey> computeGainedHealth(double previousHp, double currentHp) {
        return getHpBarPart(previousHp, currentHp);
    }
}
