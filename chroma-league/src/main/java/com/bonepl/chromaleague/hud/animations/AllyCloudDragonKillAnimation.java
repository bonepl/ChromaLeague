package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

public class AllyCloudDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyCloudDragonKillAnimation() {
        super(8, DragonType.CLOUD.getColor());
    }
}
