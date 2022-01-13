package net.booone.chromaleague.hud.colors;

import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.MultiTransitionColor;
import net.booone.razersdk.color.StaticColor;

import java.security.SecureRandom;

public class ChemtechColor implements Color {
    final MultiTransitionColor chemtechColor = new MultiTransitionColor.Builder(CLColor.CHEMTECH)
            .addTransition(StaticColor.YELLOW, 10)
            .addTransition(CLColor.CHEMTECH, 10)
            .addTransition(new StaticColor(25, 60, 0), 5)
            .looped(5)
            .build();

    public ChemtechColor() {
        int random = new SecureRandom().nextInt(chemtechColor.getTotalTransitions());
        for (int i = 0; i < random; i++) {
            chemtechColor.getColor();
        }
    }

    @Override
    public StaticColor getColor() {
        return chemtechColor.getColor();
    }
}
