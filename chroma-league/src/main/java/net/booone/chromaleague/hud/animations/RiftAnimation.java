package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.rest.eventdata.DragonType;
import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.sdk.CircularRzKeySelector;
import net.booone.razersdk.sdk.RzKey;

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
        String mapTerrain = RunningState.getGameState().getGameStats().mapTerrain();
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
