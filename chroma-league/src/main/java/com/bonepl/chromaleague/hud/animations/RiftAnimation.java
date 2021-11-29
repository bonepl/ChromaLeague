package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.sdk.CircularRzKeySelector;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Set;

public class RiftAnimation extends AnimatedFrame {

    public RiftAnimation() {
        CircularRzKeySelector circularRzKeySelector = new CircularRzKeySelector(RzKey.RZKEY_H, 2);
        Color color = getColorForMapTerrain();

        Set<RzKey> nextLayer = circularRzKeySelector.getNextLayer();
        while (!nextLayer.isEmpty()) {
            addAnimationFrame(3, new SimpleFrame(nextLayer, color));
            nextLayer = circularRzKeySelector.getNextLayer();
        }
    }

    private static Color getColorForMapTerrain() {
        String mapTerrain = RunningState.getGameState().getEventData().getMapTerrain();
        return switch (mapTerrain) {
            case "Mountain" -> DragonType.MOUNTAIN.getColor();
            case "Ocean" -> DragonType.OCEAN.getColor();
            case "Cloud" -> DragonType.CLOUD.getColor();
            case "Infernal" -> DragonType.INFERNAL.getColor();
            case "Hextech" -> DragonType.HEXTECH.getColor();
            case "Chemtech" -> DragonType.CHEMTECH.getColor();
            default -> throw new IllegalArgumentException("Something went wrong, no dragon type for mapTerrain: " + mapTerrain);
        };
    }
}
