package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class WindColor implements Color {
    int steps = 10;

    MultiTransitionColor windColor = new MultiTransitionColor.Builder(new StaticColor(15, 18, 18))
            .addTransition(CLColor.AIR, 10)
            .looped(10)
            .build();

    public WindColor() {
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < secureRandom.nextInt(steps * 2); i++) {
            windColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return windColor.getColor();
    }
}
