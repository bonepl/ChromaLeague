package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.rest.eventdata.DragonType;

import static net.booone.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public abstract class EnemyDragonKillBlinkingAnimation extends AllyDragonKillBlinkingAnimation {

    public EnemyDragonKillBlinkingAnimation(DragonType dragonType) {
        super(BLACKWIDOW_FUNCTIONAL, dragonType);
    }
}
