package com.bonepl.chromaleague.league.hud;

import com.bonepl.chromaleague.razer.effects.Color;

public enum ResourceType {
    MANA(Color.BLUE),
    ENERGY(Color.YELLOW);

    Color color;

    ResourceType(Color color) {
        this.color = color;
    }

    public static ResourceType parseResType(String value) {
        if ("ENERGY".equals(value)) {
            return ENERGY;
        }
        return MANA;
    }
}
