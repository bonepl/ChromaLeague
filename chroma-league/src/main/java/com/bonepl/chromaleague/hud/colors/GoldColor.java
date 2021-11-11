package com.bonepl.chromaleague.hud.colors;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.MultiTransitionColor;
import com.bonepl.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class GoldColor implements Color {
    private static final StaticColor DARKER_YELLOW = new StaticColor(60, 60, 0);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final float BLINK_CHANCE = 0.02f;
    private int blinkingCountdown;
    MultiTransitionColor blinkColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
            .addTransition(StaticColor.WHITE, 3)
            .looped(3)
            .build();
    MultiTransitionColor goldColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
            .addTransition(DARKER_YELLOW, 20)
            .looped(20)
            .build();

    @Override
    public StaticColor getColor() {
        if (blinkingCountdown == 0) {
            if (SECURE_RANDOM.nextFloat() <= BLINK_CHANCE) {
                blinkingCountdown = 4;
            }
            return goldColor.getColor();
        } else {
            blinkingCountdown--;
            return blinkColor.getColor();
        }
    }
}
