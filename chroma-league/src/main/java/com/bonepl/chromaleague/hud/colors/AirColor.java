package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class AirColor implements Color {
    MultiTransitionColor windColor = new MultiTransitionColor.Builder(new StaticColor(15, 18, 18))
            .addTransition(CLColor.AIR, 10)
            .looped(10)
            .build();

    public AirColor() {
        int random = new SecureRandom().nextInt(20);
        for (int i = 0; i < random; i++) {
            windColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return windColor.getColor();
    }
}
