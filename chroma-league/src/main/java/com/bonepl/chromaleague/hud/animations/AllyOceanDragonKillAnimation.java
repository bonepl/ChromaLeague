package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.DragonType;

public class AllyOceanDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyOceanDragonKillAnimation() {
        super(8, DragonType.OCEAN.getColor());
    }
}
