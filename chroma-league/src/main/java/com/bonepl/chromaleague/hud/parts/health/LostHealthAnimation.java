package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;
import java.util.stream.IntStream;

import static com.bonepl.chromaleague.hud.parts.health.HealthBar.getHpBarPart;

public class LostHealthAnimation extends AnimatedFrame {
    private static final int LOST_HEALTH_CHANGE_STEPS = 5;

    public LostHealthAnimation(double previousHp, double currentHp) {
        createLostHealthAnimation(previousHp, currentHp);
    }

    private void createLostHealthAnimation(double previousHp, double currentHp) {
        final List<RzKey> rzKeys = computeLostHealth(previousHp, currentHp);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(Color.RED, Background.BACKGROUND_COLOR, LOST_HEALTH_CHANGE_STEPS);
            IntStream.range(0, LOST_HEALTH_CHANGE_STEPS).forEach(i -> addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getNextColor())));
        }
    }

    private static List<RzKey> computeLostHealth(double previousHp, double currentHp) {
        return getHpBarPart(currentHp, previousHp);
    }
}
