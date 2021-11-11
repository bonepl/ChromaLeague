package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.hud.colors.AirColor;
import com.bonepl.chromaleague.hud.colors.FireColor;
import com.bonepl.chromaleague.hud.colors.MountainColor;
import com.bonepl.chromaleague.hud.colors.OceanColor;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

import java.util.Objects;
import java.util.function.Supplier;

public enum DragonType {
    CLOUD("Air", AirColor::new),
    ELDER("Elder", () -> StaticColor.WHITE),
    INFERNAL("Fire", FireColor::new),
    MOUNTAIN("Earth", MountainColor::new),
    OCEAN("Water", OceanColor::new);

    private final String apiType;
    private final Supplier<Color> color;

    DragonType(String apiType, Supplier<Color> color) {
        this.apiType = apiType;
        this.color = color;
    }

    public Color getColor() {
        return color.get();
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
