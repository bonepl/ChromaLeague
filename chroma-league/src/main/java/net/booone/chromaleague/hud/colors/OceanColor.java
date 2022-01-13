package net.booone.chromaleague.hud.colors;

import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.MultiTransitionColor;
import net.booone.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class OceanColor implements Color {
    private static final StaticColor DARKER_OCEAN = new StaticColor(0, 60, 60);
    final MultiTransitionColor oceanColor = new MultiTransitionColor.Builder(DARKER_OCEAN)
            .addTransition(CLColor.OCEAN, 10)
            .addTransition(DARKER_OCEAN, 10)
            .addTransition(CLColor.OCEAN, 10)
            .addTransition(new StaticColor(30, 40, 40), 5)
            .looped(5)
            .build();

    public OceanColor() {
        int random = new SecureRandom().nextInt(oceanColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            oceanColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return oceanColor.getColor();
    }
}
