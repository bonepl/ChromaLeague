package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class OceanColor implements Color {
    private static final StaticColor DARKER_OCEAN = new StaticColor(0, 60, 60);
    MultiTransitionColor oceanColor = new MultiTransitionColor.Builder(DARKER_OCEAN)
            .addTransition(CLColor.OCEAN, 10)
            .addTransition(DARKER_OCEAN, 10)
            .addTransition(CLColor.OCEAN, 10)
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
