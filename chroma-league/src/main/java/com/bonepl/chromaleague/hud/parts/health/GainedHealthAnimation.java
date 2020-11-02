package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.hud.colors.CLColor;
import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;
import java.util.stream.IntStream;

import static com.bonepl.chromaleague.hud.parts.health.HealthBar.getHpBarPart;

public class GainedHealthAnimation extends AnimatedFrame {
    private static final int GAINED_HEALTH_CHANGE_STEPS = 5;

    public GainedHealthAnimation(double previousHp, double currentHp) {
        createGainedHealthAnimation(previousHp, currentHp);
    }

    private void createGainedHealthAnimation(double previousHp, double currentHp) {
        final List<RzKey> rzKeys = computeGainedHealth(previousHp, currentHp);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(Color.WHITE, CLColor.HEALTH, GAINED_HEALTH_CHANGE_STEPS);
            IntStream.range(0, GAINED_HEALTH_CHANGE_STEPS).forEach(i -> addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getNextColor())));
        }
    }

    private static List<RzKey> computeGainedHealth(double previousHp, double currentHp) {
        return getHpBarPart(previousHp, currentHp);
    }
}
