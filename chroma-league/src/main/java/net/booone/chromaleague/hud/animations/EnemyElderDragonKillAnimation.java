package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.rest.eventdata.DragonType;

public class EnemyElderDragonKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyElderDragonKillAnimation() {
        super(DragonType.ELDER.getColor());
    }
}
