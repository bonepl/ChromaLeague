package com.bonepl.chromaleague.rest.gamestats;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum MapTerrain {
    CHEMTECH("Chemtech", DragonType.CHEMTECH),
    CLOUD("Air", DragonType.CLOUD),
    HEXTECH("Hextech", DragonType.HEXTECH),
    INFERNAL("Infernal", DragonType.INFERNAL),
    MOUNTAIN("Earth", DragonType.MOUNTAIN),
    OCEAN("Water", DragonType.OCEAN);

    private String apiType;
    private DragonType dragonType;

    MapTerrain(String apiType, DragonType dragonType) {
        this.apiType = apiType;
        this.dragonType = dragonType;
    }

    public static MapTerrain fromApiType(String apiType) {
        return Arrays.stream(values())
                .filter(mt -> mt.apiType.equals(apiType))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("MapTerrain for apiType " + apiType + " does not exist"));
    }

    public Color getBackgroundColor() {
        StaticColor color = dragonType.getColor();
        return new StaticColor(reduceColor(color.red()), reduceColor(color.green()), reduceColor(color.blue()));
    }

    public int reduceColor(int color) {
        return (int) (0.1 * color);
    }
}
