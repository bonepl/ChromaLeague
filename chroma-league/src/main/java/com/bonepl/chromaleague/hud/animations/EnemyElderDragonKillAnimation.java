package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

public class EnemyElderDragonKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyElderDragonKillAnimation() {
        super(DragonType.ELDER.getColor());
    }
}
