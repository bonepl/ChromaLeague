package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

public class AllyInfernalDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyInfernalDragonKillAnimation() {
        super(8, DragonType.INFERNAL.getColor());
    }
}
