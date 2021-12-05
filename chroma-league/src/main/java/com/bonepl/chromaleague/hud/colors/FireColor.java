package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class FireColor implements Color {
    final MultiTransitionColor fireColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
            .addTransition(StaticColor.ORANGE, 5)
            .addTransition(StaticColor.RED, 10)
            .addTransition(StaticColor.ORANGE, 10)
            .looped(5)
            .build();

    public FireColor() {
        int random = new SecureRandom().nextInt(fireColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            fireColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return fireColor.getColor();
    }
}
