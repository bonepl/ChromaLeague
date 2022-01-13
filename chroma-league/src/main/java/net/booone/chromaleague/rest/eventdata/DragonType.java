package net.booone.chromaleague.rest.eventdata;

import net.booone.chromaleague.hud.colors.*;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

public enum DragonType {
    CHEMTECH("Chemtech", CLColor.CHEMTECH, ChemtechColor::new),
    CLOUD("Air", CLColor.AIR, AirColor::new),
    ELDER("Elder", StaticColor.WHITE, () -> StaticColor.WHITE),
    HEXTECH("Hextech", StaticColor.BLUE, HextechColor::new),
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

    public StaticColor getColor() {
        return color;
    }

    public Color getSoulColor() {
        return soulColor.get();
    }

    public static DragonType fromApiType(String apiType) {
        return Arrays.stream(values())
                .filter(v -> Objects.equals(v.apiType, apiType))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Couldn't find dragon type for apiType " + apiType));
    }
}
