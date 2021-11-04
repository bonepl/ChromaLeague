package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class FireColor implements Color {
    int steps = 10;
    BreathingColor breathingColor = new BreathingColor(StaticColor.RED, StaticColor.YELLOW, steps);

    public FireColor() {
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < secureRandom.nextInt(steps * 2); i++) {
            breathingColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return breathingColor.getColor();
    }
}
