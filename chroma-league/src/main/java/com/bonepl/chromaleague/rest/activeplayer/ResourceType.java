package com.bonepl.chromaleague.rest.activeplayer;

import com.bonepl.razersdk.animation.Color;

public enum ResourceType {
    MANA(Color.BLUE),
    ENERGY(Color.YELLOW);

    final Color color;

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
