package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class GoldColor implements Color {
    MultiTransitionColor fireColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
            .addTransition(new StaticColor(60, 60, 0), 20)
            .addTransition(StaticColor.YELLOW, 20)
            .addTransition(new StaticColor(60, 60, 0), 20)
            .addTransition(StaticColor.YELLOW, 20)
            .addTransition(new StaticColor(60, 60, 0), 20)
            .addTransition(StaticColor.YELLOW, 20)
            .addTransition(new StaticColor(60, 60, 0), 20)
            .addTransition(StaticColor.WHITE, 3)
            .looped(5)
            .build();

    public GoldColor(int steps) {
        for (int i = 0; i < steps; i++) {
            fireColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return fireColor.getColor();
    }
}
