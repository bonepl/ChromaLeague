package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.DragonType;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public class EnemyMountainDragonKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyMountainDragonKillAnimation() {
        super(BLACKWIDOW_FUNCTIONAL, 8, DragonType.MOUNTAIN.getColor());
    }
}
