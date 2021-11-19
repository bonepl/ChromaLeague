package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class MountainColor implements Color {
    MultiTransitionColor mountainColor = new MultiTransitionColor.Builder(new StaticColor(8,3,0))
            .addTransition(StaticColor.BROWN, 10)
            .addTransition(new StaticColor(8,3,0), 10)
            .addTransition(new StaticColor(64,24,0),4)
            .looped(4)
            .build();

    public MountainColor() {
        int random = new SecureRandom().nextInt(mountainColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            mountainColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return mountainColor.getColor();
    }
}
