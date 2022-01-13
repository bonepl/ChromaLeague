package net.booone.chromaleague.hud.colors;

import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.MultiTransitionColor;
import net.booone.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class MountainColor implements Color {
    final MultiTransitionColor mountainColor = new MultiTransitionColor.Builder(new StaticColor(8, 3, 0))
            .addTransition(StaticColor.BROWN, 10)
            .addTransition(new StaticColor(8, 3, 0), 10)
            .addTransition(new StaticColor(64, 24, 0), 4)
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
