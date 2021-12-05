package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class HextechColor implements Color {
    final MultiTransitionColor hextechColor = new MultiTransitionColor.Builder(StaticColor.BLUE)
            .addTransition(StaticColor.BLUE, 20)
            .addTransition(StaticColor.WHITE, 3)
            .looped(3)
            .build();

    public HextechColor() {
        int random = new SecureRandom().nextInt(hextechColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            hextechColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return hextechColor.getColor();
    }
}
