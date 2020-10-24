package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public class EnemyCloudDragonKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyCloudDragonKillAnimation() {
        super(BLACKWIDOW_FUNCTIONAL, 8, DragonType.CLOUD.getColor());
    }
}
