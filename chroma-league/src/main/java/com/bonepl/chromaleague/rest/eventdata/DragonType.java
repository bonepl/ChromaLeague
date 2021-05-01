package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.hud.colors.CLColor;
import com.bonepl.razersdk.animation.Color;

import java.util.Objects;

public enum DragonType {
    CLOUD("Air", CLColor.AIR),
    ELDER("Elder", Color.WHITE),
    INFERNAL("Fire", Color.RED),
    MOUNTAIN("Earth", Color.BROWN),
    OCEAN("Water", CLColor.OCEAN);

    private final String apiType;
    private final Color color;

    DragonType(String apiType, Color color) {
        this.apiType = apiType;
        this.color = color;
    }

    public String getApiType() {
        return apiType;
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
