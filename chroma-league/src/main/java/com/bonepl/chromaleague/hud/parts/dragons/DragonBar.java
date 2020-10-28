package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.LayeredFrame;

public class DragonBar extends AnimatedFrame {
    private final ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();

    @Override
    public Frame getFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new KilledDragonsBar());
        if (GameStateHelper.hasElderBuff()) {
            layeredFrame.addFrame(elderBuffAnimation);
        }
        addAnimationFrame(layeredFrame);
        return super.getFrame();
    }
}
