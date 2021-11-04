package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.hud.colors.CLColor;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

import java.util.Objects;

public enum DragonType {
    CLOUD("Air", CLColor.AIR),
    ELDER("Elder", StaticColor.WHITE),
    INFERNAL("Fire", StaticColor.RED),
    MOUNTAIN("Earth", StaticColor.BROWN),
    OCEAN("Water", CLColor.OCEAN);

    private final String apiType;
    private final Color color;

    DragonType(String apiType, Color color) {
        this.apiType = apiType;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static DragonType fromApiType(String apiType) {
        for (DragonType value : values()) {
            if (Objects.equals(value.apiType, apiType)) {
                return value;
            }
        }
        return null;
    }
}
