package net.booone.chromaleague.hud.parts.health;

import net.booone.chromaleague.hud.parts.Background;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.color.TransitionColor;
import net.booone.razersdk.sdk.RzKey;

import java.util.List;
import java.util.stream.IntStream;

public class LostHealthAnimation extends AnimatedFrame {
    private static final int LOST_HEALTH_CHANGE_STEPS = 5;

    public LostHealthAnimation(double previousHp, double currentHp) {
        createLostHealthAnimation(previousHp, currentHp);
    }

    private void createLostHealthAnimation(double previousHp, double currentHp) {
        final List<RzKey> rzKeys = computeLostHealth(previousHp, currentHp);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(StaticColor.RED, Background.DEFAULT_BACKGROUND_COLOR, LOST_HEALTH_CHANGE_STEPS);
            IntStream.range(0, LOST_HEALTH_CHANGE_STEPS).forEach(i -> addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getColor())));
        }
    }

    private static List<RzKey> computeLostHealth(double previousHp, double currentHp) {
        return HealthBar.getHpBarPart(currentHp, previousHp);
    }
}
