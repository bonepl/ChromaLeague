package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.DragonType;

public class AllyElderDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyElderDragonKillAnimation() {
        super(8, DragonType.ELDER.getColor());
    }
}
