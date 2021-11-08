package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class MountainColor implements Color {
    int steps = 10;

    MultiTransitionColor oceanColor = new MultiTransitionColor.Builder(new StaticColor(8,3,0))
            .addTransition(StaticColor.BROWN, 10)
            .looped(10)
            .build();

    public MountainColor() {
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < secureRandom.nextInt(steps * 2); i++) {
            oceanColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return oceanColor.getColor();
    }
}
