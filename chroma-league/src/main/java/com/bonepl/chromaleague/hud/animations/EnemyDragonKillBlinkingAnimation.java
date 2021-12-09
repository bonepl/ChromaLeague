package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public abstract class EnemyDragonKillBlinkingAnimation extends AllyDragonKillBlinkingAnimation {

    public EnemyDragonKillBlinkingAnimation(DragonType dragonType) {
        super(BLACKWIDOW_FUNCTIONAL, dragonType);
    }
}
