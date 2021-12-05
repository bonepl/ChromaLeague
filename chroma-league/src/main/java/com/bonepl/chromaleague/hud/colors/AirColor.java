package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class AirColor implements Color {
    final MultiTransitionColor windColor = new MultiTransitionColor.Builder(new StaticColor(15, 18, 18))
            .addTransition(CLColor.AIR, 10)
            .addTransition(new StaticColor(15, 18, 18), 10)
            .addTransition(new StaticColor(60, 75, 75), 5)
            .looped(5)
            .build();

    public AirColor() {
        int random = new SecureRandom().nextInt(windColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            windColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return windColor.getColor();
    }
}
