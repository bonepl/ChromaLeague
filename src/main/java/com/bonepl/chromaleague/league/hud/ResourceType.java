package com.bonepl.chromaleague.league.hud;

import com.bonepl.chromaleague.razer.effects.Color;

public enum ResourceType {
    MANA(Color.BLUE),
    ENERGY(Color.YELLOW);

    Color color;

    ResourceType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static ResourceType from(String value) {
        if ("ENERGY".equals(value)) {
            return ENERGY;
        }
        return MANA;
    }
}
