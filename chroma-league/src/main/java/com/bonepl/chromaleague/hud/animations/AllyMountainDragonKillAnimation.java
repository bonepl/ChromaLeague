package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

public class AllyMountainDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyMountainDragonKillAnimation() {
        super(8, DragonType.MOUNTAIN.getColor());
    }
}
