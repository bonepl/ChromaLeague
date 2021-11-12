package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.hud.colors.*;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

import java.util.Objects;
import java.util.function.Supplier;

public enum DragonType {
    CLOUD("Air", CLColor.AIR, AirColor::new),
    ELDER("Elder", StaticColor.WHITE, () -> StaticColor.WHITE),
    INFERNAL("Fire", StaticColor.RED, FireColor::new),
    MOUNTAIN("Earth", StaticColor.BROWN, MountainColor::new),
    OCEAN("Water", CLColor.OCEAN, OceanColor::new);

    private final String apiType;
    private final StaticColor color;
    private final Supplier<Color> soulColor;

    DragonType(String apiType, StaticColor color, Supplier<Color> soulColor) {
        this.apiType = apiType;
        this.color = color;
        this.soulColor = soulColor;
    }

    public Color getColor() {
        return color;
    }

    public Color getSoulColor() {
        return soulColor.get();
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
