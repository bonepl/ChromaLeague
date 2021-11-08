package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class OceanColor implements Color {
    MultiTransitionColor oceanColor = new MultiTransitionColor.Builder(new StaticColor(0, 0, 80))
            .addTransition(StaticColor.BLUE, 10)
            .addTransition(new StaticColor(0, 0, 80), 10)
            .addTransition(StaticColor.BLUE, 10)
            .addTransition(new StaticColor(30, 40, 40), 5)
            .looped(5)
            .build();

    public OceanColor() {
        int random = new SecureRandom().nextInt(40);
        for (int i = 0; i < random; i++) {
            oceanColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return oceanColor.getColor();
    }
}
