package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.rest.eventdata.DragonType;

public class AllyElderDragonKillAnimation extends StaticBlinkingAnimation {
    public AllyElderDragonKillAnimation() {
        super(DragonType.ELDER.getColor());
    }
}
