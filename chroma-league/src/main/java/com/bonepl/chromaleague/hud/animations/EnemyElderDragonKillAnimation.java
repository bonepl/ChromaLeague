package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.model.DragonType;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public class EnemyElderDragonKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyElderDragonKillAnimation() {
        super(BLACKWIDOW_FUNCTIONAL, 8, DragonType.ELDER.getColor());
    }
}
