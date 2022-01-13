package net.booone.chromaleague.hud.parts.dragons;

import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.LayeredFrame;

public class DragonBar extends AnimatedFrame {
    private final ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();
    private final KilledDragonsBar killedDragonsBar = new KilledDragonsBar();

    @Override
    public Frame getFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(killedDragonsBar);
        if (GameStateHelper.hasElderBuff()) {
            layeredFrame.addFrame(elderBuffAnimation);
        }
        addAnimationFrame(layeredFrame);
        return super.getFrame();
    }
}
